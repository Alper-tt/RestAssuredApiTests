package com.booking.services;

import com.booking.models.request.Booking;
import com.booking.models.request.BookingDates;
import com.booking.models.response.CreateBookingResponse;
import groovy.json.JsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingService extends BaseTest{

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
                .spec(responseSpec)
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

    public Booking updateBookingById(String token, int bookingId, String firstname, String lastname, int totalprice){
        Booking booking = getBookingById(bookingId);
        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(totalprice);

        return given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body(booking)
                .when()
                .put("/booking/"+bookingId)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().body().as(Booking.class);
    }

    public Booking partiallyUpdateBookingById(String token, int bookingId, String firstname, String lastname){
        JsonBuilder updatedDetails = new JsonBuilder(
                Map.of("firstname", firstname, "lastname", lastname, "totalprice", 5)
        );

        return given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body(updatedDetails.toString())
                .when()
                .patch("/booking/"+bookingId)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().body().as(Booking.class);
    }

    public void checkBookingSchemaValidation(int bookingId){
        given()
                .spec(requestSpec)
                .when()
                .get("/booking/"+bookingId)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-get.json"));
    }
}
