package com.booking.steps;

import com.booking.context.ScenarioContext;
import com.booking.services.BookingService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class DeleteBookingSteps{
    private final ScenarioContext scenarioContext;
    private final BookingService bookingService;

    public DeleteBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }

    @And("Kullanici rezervasyonu iptal etsin")
    public void deleteBooking(){
        bookingService.deleteBooking(scenarioContext.getToken(), scenarioContext.getBookingResponse().getBookingId());
    }

    @Then("Rezervasyon basarili bir sekilde silinsin")
    public void isBookingDeleted(){
        Assertions.assertFalse(bookingService.isBookingPresent(scenarioContext.getBookingResponse().getBookingId()));
    }
}
