package com.booking.services;

import com.booking.models.request.Booking;
import com.booking.models.request.BookingDates;
import com.booking.models.response.CreateBookingResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class BookingService extends BaseTest{

    RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setAccept("application/json")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured()))
            .build();
    ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(2000L))
            .build();

    public CreateBookingResponse createBooking(String firstname, String lastname, int totalprice){
        BookingDates bookingDates = new BookingDates("2013-02-23", "2014-10-23");
        Booking booking = new Booking(firstname, lastname, totalprice, true, bookingDates, "Lunch");

        CreateBookingResponse bookingResponse = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        Assertions.assertEquals(bookingResponse.getBooking().getFirstname(), firstname);
        Assertions.assertEquals(bookingResponse.getBooking().getLastname(), lastname);
        return  bookingResponse;
    }

    public void deleteBooking(String token, int bookingId){
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .when()
                .delete("/booking/"+bookingId)
                .then()
                .spec(responseSpec)
                .statusCode(201);
    }

    public Booking getBookingById(int bookingId){
        return given()
                .spec(requestSpec)
                .when()
                .get("/booking/"+bookingId)
                .then()
                .statusCode(200)
                .extract().as(Booking.class);
    }

    public boolean isBookingPresent(int bookingId){
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booking/"+bookingId);

        return response.statusCode() == 200;
    }
}
