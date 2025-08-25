package com.booking.hooks;

import com.booking.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.lessThan;

public class Hooks {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    @Before
    public void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setAccept("application/json")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured()))
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(2000L))
                .build();
    }

    @After
    public void tearDown() throws IOException, InterruptedException{
        Process process = new ProcessBuilder(
                "allure", "generate", "target/allure-results", "-o", "target/allure-report", "--clean"
        ).inheritIO().start();
        process.waitFor();
    }
}
