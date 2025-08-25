package com.booking.tests;

import com.booking.models.request.Credential;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteBookingTests extends BaseTest{

    @Test
    public void deleteBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        Integer bookingId = createBookingId("alper", "topraktepe", 7);

        given(requestSpec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .when()
                .delete("/booking/"+bookingId)
                .then()
                .statusCode(201);

        given(requestSpec)
                .when()
                .get("/booking/"+bookingId)
                .then()
                .statusCode(404);
    }
}
