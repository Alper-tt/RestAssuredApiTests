package com.booking.context;

import com.booking.models.request.Booking;
import com.booking.models.response.CreateBookingResponse;

public class ScenarioContext {
    private String token;
    private CreateBookingResponse bookingResponse;
    private Booking booking;

    public ScenarioContext() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CreateBookingResponse getBookingResponse() {
        return bookingResponse;
    }

    public void setBookingResponse(CreateBookingResponse bookingResponse) {
        this.bookingResponse = bookingResponse;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
