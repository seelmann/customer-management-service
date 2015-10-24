package com.example.cms.customerservice.web;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.cms.customerservice.Application;
import com.example.cms.customerservice.DemoData;
import com.example.cms.customerservice.domain.Customer;
import com.example.cms.customerservice.service.CustomerRepository;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:${test.properties:test-hsql.properties}")
@WebIntegrationTest({ "server.port=0" })
public class CustomerControllerIT {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private CustomerRepository repository;

    private RequestSpecification spec;

    @Before
    public void setUp() throws Exception {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:" + port + "/");
        this.spec = builder.build();
    }

    @Test
    public void testGetAllOK() {
        Customer customer = DemoData.c1();
        int NUM = 1001;
        for(int i=0; i<NUM; i++) {
            repository.save(DemoData.c1());
        }

        given(spec)
        .when()
            .accept("application/json")
            .get("/customers")
        .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
            .body("", hasSize(1001))
            .body("[0].id", notNullValue())
            .body("[0].firstname", equalTo(customer.getFirstname()))
            .body("[0].lastname", equalTo(customer.getLastname()))
            .body("[0].age", equalTo(customer.getAge()))
            .body("[0].emailAddress", equalTo(customer.getEmailAddress()))
            .body("[0].privateAddress.street", equalTo(customer.getPrivateAddress().getStreet()))
            .body("[0].privateAddress.city", equalTo(customer.getPrivateAddress().getCity()))
            .body("[0].privateAddress.zipCode", equalTo(customer.getPrivateAddress().getZipCode()))
            .body("[0].privateAddress.country", equalTo(customer.getPrivateAddress().getCountry()))
            .body("[0].companyAddress.street", equalTo(customer.getCompanyAddress().getStreet()))
            .body("[0].companyAddress.city", equalTo(customer.getCompanyAddress().getCity()))
            .body("[0].companyAddress.zipCode", equalTo(customer.getCompanyAddress().getZipCode()))
            .body("[0].companyAddress.country", equalTo(customer.getCompanyAddress().getCountry()))
        ;
    }

    @Test
    public void testGetOneOK() {
        Customer customer = DemoData.c2();
        Long id = repository.save(customer).getId();

        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/" + id)
        .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
            .body("id", notNullValue())
            .body("firstname", equalTo(customer.getFirstname()))
            .body("lastname", equalTo(customer.getLastname()))
            .body("age", equalTo(customer.getAge()))
            .body("emailAddress", equalTo(customer.getEmailAddress()))
            .body("privateAddress.street", equalTo(customer.getPrivateAddress().getStreet()))
            .body("privateAddress.city", equalTo(customer.getPrivateAddress().getCity()))
            .body("privateAddress.zipCode", equalTo(customer.getPrivateAddress().getZipCode()))
            .body("privateAddress.country", equalTo(customer.getPrivateAddress().getCountry()))
            .body("companyAddress.street", equalTo(customer.getCompanyAddress().getStreet()))
            .body("companyAddress.city", equalTo(customer.getCompanyAddress().getCity()))
            .body("companyAddress.zipCode", equalTo(customer.getCompanyAddress().getZipCode()))
            .body("companyAddress.country", equalTo(customer.getCompanyAddress().getCountry()))
        ;
    }

    @Test
    public void testGetOneNonExistingCustomerShouldReturn404() {
        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/" + Long.MAX_VALUE)
        .then()
            .statusCode(404)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testGetOndBadRequestShouldReturn400() {
        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/invalid")
        .then()
            .statusCode(400)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testPostOK() {
        Customer customer = DemoData.c1();

        ExtractableResponse<Response> response =
        given(spec)
        .when()
            .contentType("application/json")
            .body(customer)
            .post("/customers")
        .then()
            .statusCode(201)
            .header("Location", notNullValue())
            .header("Content-Length", "0")
            .extract()
        ;

        // assert the customer exists in DB
        String location = response.headers().get("Location").getValue();
        String id = location.substring(location.lastIndexOf("/") + 1);
        assertThat(repository.exists(Long.valueOf(id)), equalTo(true));
        assertEqual(customer, repository.findOne(Long.valueOf(id)));

        // assert the location return the customer
        Customer receivedCustomer = 
        given(spec)
        .when()
            .accept("application/json")
            .get(location)
        .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
            .extract().as(Customer.class)
        ;
        assertEqual(customer, receivedCustomer);
    }

    @Test
    public void testPostToResourceShouldReturn405() {
        Customer customer = DemoData.c1();

        given(spec)
        .when()
            .contentType("application/json")
            .body(customer)
            .post("/customers/4")
        .then()
            .statusCode(405)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testPostInvalidBodyShouldReturn400() {
        given(spec)
        .when()
            .contentType("application/json")
            .body("{invalid}")
            .post("/customers")
        .then()
            .statusCode(400)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testPutOK() {
        Customer original = DemoData.c1();
        Long id = repository.save(original).getId();

        Customer modified = DemoData.c2();

        given(spec)
        .when()
            .contentType("application/json")
            .body(modified)
            .put("/customers/" + id)
        .then()
            .statusCode(204)
        ;

        // assert the customer was updated in DB
        assertEqual(modified, repository.findOne(id));

        // assert the modificated customer can be retrieved via API
        Customer receivedCustomer = 
        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/" + id)
        .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
            .extract().as(Customer.class)
        ;
        assertEqual(modified, receivedCustomer);
    }

    @Test
    public void testPutNonExistingReturn404() {
        given(spec)
        .when()
            .contentType("application/json")
            .body(DemoData.c1())
            .put("/customers/" + Long.MAX_VALUE)
        .then()
            .statusCode(404)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testPutInvalidBodyShouldReturn400() {
        given(spec)
        .when()
            .contentType("application/json")
            .body("{invalid}")
            .put("/customers/5")
        .then()
            .statusCode(400)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testDeleteOK() {
        Long id = repository.save(DemoData.c1()).getId();

        given(spec)
        .when()
            .delete("/customers/" + id)
        .then()
            .statusCode(204)
        ;

        // assert the customer was deleted in DB
        assertThat(repository.exists(id), equalTo(false));

        // assert the deleted customer can not be retrieved via API
        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/" + id)
        .then()
            .statusCode(404)
        ;
    }

    @Test
    public void testDeleteNonExistingReturn404() {
        given(spec)
        .when()
            .delete("/customers/" + Long.MAX_VALUE)
        .then()
            .statusCode(404)
        ;
    }

    private void assertEqual(Customer expected, Customer actual) {
        assertThat(actual.getFirstname(), equalTo(expected.getFirstname()));
        assertThat(actual.getLastname(), equalTo(expected.getLastname()));
        assertThat(actual.getEmailAddress(), equalTo(expected.getEmailAddress()));
        assertThat(actual.getAge(), equalTo(expected.getAge()));
        assertThat(actual.getPrivateAddress().getStreet(), equalTo(expected.getPrivateAddress().getStreet()));
        assertThat(actual.getPrivateAddress().getCity(), equalTo(expected.getPrivateAddress().getCity()));
        assertThat(actual.getPrivateAddress().getZipCode(), equalTo(expected.getPrivateAddress().getZipCode()));
        assertThat(actual.getPrivateAddress().getCountry(), equalTo(expected.getPrivateAddress().getCountry()));
        assertThat(actual.getCompanyAddress().getStreet(), equalTo(expected.getCompanyAddress().getStreet()));
        assertThat(actual.getCompanyAddress().getCity(), equalTo(expected.getCompanyAddress().getCity()));
        assertThat(actual.getCompanyAddress().getZipCode(), equalTo(expected.getCompanyAddress().getZipCode()));
        assertThat(actual.getCompanyAddress().getCountry(), equalTo(expected.getCompanyAddress().getCountry()));
    }
}