package com.example.cms.customerservice.web;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.cms.customerservice.Application;
import com.example.cms.customerservice.DemoData;
import com.example.cms.customerservice.domain.Customer;
import com.example.cms.customerservice.service.CustomerRepository;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({ "server.port=0" })
public class CustomerControllerTest {

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
    public void testGetRequest() throws Exception {
        Customer customer = DemoData.C2;
        Long id = repository.save(customer).getId();

        given(spec)
        .when()
            .accept("application/json")
            .get("/customers/" + id)
        .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
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

}