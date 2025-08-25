package com.booking.tests;

import com.booking.models.request.Booking;
import com.booking.models.request.BookingDates;
import com.booking.models.request.Credential;
import com.booking.models.response.AuthToken;
import com.booking.models.response.CreateBookingResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;

    @BeforeAll
    static void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setAccept("application/json")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured()))
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(2000L))
                .build();
    }

    @AfterAll
    static void generateReport() throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                "allure", "generate", "target/allure-results", "-o", "target/allure-report", "--clean"
        ).inheritIO().start();
        process.waitFor();
    }

    protected String createToken(Credential credential) {
        AuthToken authToken =
                given(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .when()
                        .post("/auth")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthToken.class);

        assertThat(authToken, notNullValue());
        return authToken.getToken();
    }

    protected CreateBookingResponse createBooking(String firstname, String lastname, int totalprice) {
        CreateBookingResponse bookingResponse =
                given(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(new Booking(firstname, lastname, totalprice, false,
                                new BookingDates("2013-02-23", "2014-10-23"), "Lunch"))
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(CreateBookingResponse.class);

        assertThat(bookingResponse.getBooking().getFirstname(), equalTo(firstname));
        assertThat(bookingResponse.getBooking().getLastname(), equalTo(lastname));
        return bookingResponse;
    }

    protected Integer createBookingId(String firstname, String lastname, int totalprice) {
        CreateBookingResponse bookingResponse = createBooking(firstname, lastname, totalprice);
        return bookingResponse.getBookingId();
    }
}
