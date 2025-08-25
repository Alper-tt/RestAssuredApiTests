package com.booking.tests;

import com.booking.models.request.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetBookingByIdTests extends  BaseTest {

    @Test
    public void getBookingByIdTest(){
        int bookingId = createBookingId("alper", "test", 7);
        Booking response = given(requestSpec)
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().body().as(Booking.class);

        Assertions.assertEquals("alper",response.getFirstname());
        Assertions.assertEquals("test",response.getLastname());
        Assertions.assertEquals(7,response.getTotalprice());
    }
}
