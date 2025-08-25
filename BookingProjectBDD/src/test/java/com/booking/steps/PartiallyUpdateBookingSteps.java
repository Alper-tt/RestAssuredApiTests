package com.booking.steps;

import com.booking.context.ScenarioContext;
import com.booking.models.request.Booking;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;

public class PartiallyUpdateBookingSteps {
    private final ScenarioContext scenarioContext;
    private final BookingService bookingService;

    public PartiallyUpdateBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }

    @Then("Kullanici rezervasyon id ile rezervasyonunun bir kismini degistirsin")
    public void partiallyUpdateBookingById(){
        Booking booking = bookingService.partiallyUpdateBookingById(scenarioContext.getToken(), scenarioContext.getBookingResponse().getBookingId(), "arda", "kaya");
        scenarioContext.setBooking(booking);
    }
}
