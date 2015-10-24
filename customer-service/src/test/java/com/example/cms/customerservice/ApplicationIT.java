package com.example.cms.customerservice;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:${test.properties:test-hsql.properties}")
@WebIntegrationTest({ "server.port=0" })
public class ApplicationIT {

    @Value("${local.server.port}")
    private int port;

    private RequestSpecification spec;

    @Before
    public void setUp() throws Exception {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:" + port + "/");
        this.spec = builder.build();
    }

    @Test
    public void testGetRootShouldNotExposeInformation() {
        given(spec)
        .when()
            .accept("application/json")
            .get("/")
        .then()
            .statusCode(404)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testGetUnknownJsonResourceShouldNotExposeInformation() {
        given(spec)
        .when()
            .accept("application/json")
            .get("/invalid")
        .then()
            .statusCode(404)
            .header("Content-Length", "0")
        ;
    }

    @Test
    public void testGetUnknownHtmlResourceShouldNotExposeInformation() {
        given(spec)
        .when()
            .accept("text/html")
            .get("/invalid")
        .then()
            .statusCode(404)
            .header("Content-Length", "0")
        ;
    }

}