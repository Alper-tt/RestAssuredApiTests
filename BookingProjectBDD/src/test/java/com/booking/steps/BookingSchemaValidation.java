package com.booking.steps;

import com.booking.context.ScenarioContext;
import com.booking.services.BookingService;
import io.cucumber.java.en.And;

public class BookingSchemaValidation {
    private final ScenarioContext scenarioContext;
    private final BookingService bookingService;

    public BookingSchemaValidation(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }

    @And("Rezervasyon semasi istenilen duzende olsun")
    public void checkBookingSchemaValidation(){
        bookingService.checkBookingSchemaValidation(scenarioContext.getBookingResponse().getBookingId());
    }
}
