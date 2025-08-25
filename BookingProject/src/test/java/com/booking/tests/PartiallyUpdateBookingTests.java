package com.booking.tests;

import com.booking.models.request.Booking;
import com.booking.models.request.Credential;
import com.booking.models.response.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PartiallyUpdateBookingTests extends BaseTest{

    @Test
    public void partiallyUpdateBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        CreateBookingResponse response = createBooking("alper", "test", 7);

        Booking updatedBooking = given(requestSpec)
                .contentType(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body("""
                        {"firstname":"arda",
                        "lastname":"cont"}
                        """)
                .when()
                .patch("/booking/"+response.getBookingId())
                .then()
                .statusCode(200)
                .extract().as(Booking.class);

        Assertions.assertEquals("arda",updatedBooking.getFirstname());
        Assertions.assertEquals("cont",updatedBooking.getLastname());
    }
}
