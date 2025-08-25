package com.booking.services;

import com.booking.models.request.Credential;
import com.booking.models.response.AuthToken;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class BaseTest {

    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setAccept("application/json")
            .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured()))
            .build();
    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(2000L))
            .build();

    public String generateToken(){
        Credential credential = new Credential("admin","password123");
        AuthToken authToken =
                given()
                        .spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .when()
                        .post("https://restful-booker.herokuapp.com/auth")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthToken.class);

        assertThat(authToken, notNullValue());
        return authToken.getToken();
    }
}
