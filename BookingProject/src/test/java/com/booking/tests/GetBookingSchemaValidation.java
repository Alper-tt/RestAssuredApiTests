package com.booking.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBookingSchemaValidation extends BaseTest{

    Integer bookingId = createBookingId("alper", "test", 7);
    @Test
    public void getBookingSchemaValidation() {
        given(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-get.json"));
    }
}
