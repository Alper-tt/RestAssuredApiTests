package com.booking.tests;

import com.booking.models.response.CreateBookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookingTests(){
        CreateBookingResponse response = createBooking("alper", "test", 7);
        Assertions.assertEquals("alper", response.getBooking().getFirstname());
        Assertions.assertEquals("test", response.getBooking().getLastname());
        Assertions.assertEquals(7, response.getBooking().getTotalprice());
    }

}
