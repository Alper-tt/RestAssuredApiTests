package com.booking.tests;

import com.booking.models.request.Booking;
import com.booking.models.request.Credential;
import com.booking.models.response.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateBookingTests extends BaseTest{

    @Test
    public void updateBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        CreateBookingResponse response = createBooking("alper", "test", 7);
        Booking newBooking = response.getBooking();
        newBooking.setFirstname("arda");
        newBooking.setLastname("cont");
        newBooking.setTotalprice(5);

        Booking updatedBooking = given(requestSpec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body(newBooking)
                .when()
                .put("/booking/"+response.getBookingId())
                .then()
                .statusCode(200)
                .extract().body().as(Booking.class);

        Assertions.assertEquals("arda",updatedBooking.getFirstname());
        Assertions.assertEquals("cont",updatedBooking.getLastname());
        Assertions.assertEquals(5,updatedBooking.getTotalprice());
    }
}
