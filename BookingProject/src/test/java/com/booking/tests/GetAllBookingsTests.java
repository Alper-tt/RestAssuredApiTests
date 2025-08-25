package com.booking.tests;

import com.booking.models.request.Booking;
import com.booking.models.response.CreateBookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetAllBookingsTests extends BaseTest{

    @Test
    public void getAllBookingsTest(){
        given(requestSpec)
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .extract().body().as(List.class);
    }

    @Test
    public void getBookingsWithQueryParams() {
        CreateBookingResponse bookingResponse = createBooking("alper", "test", 7);
        requestSpec.queryParams(Map.of("firstname", bookingResponse.getBooking().getFirstname(), "lastname", bookingResponse.getBooking().getLastname()));
        List<Integer> responseList = given(requestSpec)
                .when()
                .get("/booking")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().jsonPath().getList("bookingid");

        Assertions.assertTrue(responseList.contains(bookingResponse.getBookingId()));
    }
}
