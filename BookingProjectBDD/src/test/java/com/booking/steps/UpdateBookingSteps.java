package com.booking.steps;

import com.booking.context.ScenarioContext;
import com.booking.models.request.Booking;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class UpdateBookingSteps {

    private final ScenarioContext scenarioContext;
    private final BookingService bookingService;

    public UpdateBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }

    @Then("Kullanici rezervasyon id ile rezervasyon bilgilerini degistirsin")
    public void updateBookingById(){
        Booking booking = bookingService.updateBookingById(scenarioContext.getToken(), scenarioContext.getBookingResponse().getBookingId(), "arda", "kaya", 5);
        scenarioContext.setBooking(booking);
    }

    @Then("Degistirilen rezervasyon bilgileri kontrol edilsin")
    public void checkUpdatedBooking(){
        Booking booking = bookingService.getBookingById(scenarioContext.getBookingResponse().getBookingId());
        Assertions.assertEquals("arda", booking.getFirstname());
        Assertions.assertEquals("kaya", booking.getLastname());
        Assertions.assertEquals(5, booking.getTotalprice());
    }
}
