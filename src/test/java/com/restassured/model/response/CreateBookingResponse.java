package com.restassured.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.restassured.model.request.Booking;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookingResponse {
    @JsonProperty("bookingid")
    public int bookingId;
    public Booking booking;
    public CreateBookingResponse() {}
}
