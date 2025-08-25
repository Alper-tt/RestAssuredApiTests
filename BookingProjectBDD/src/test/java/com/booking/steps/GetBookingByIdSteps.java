package com.booking.steps;
import com.booking.context.ScenarioContext;
import com.booking.models.request.Booking;
import com.booking.services.BookingService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class GetBookingByIdSteps{

    private final ScenarioContext scenarioContext;
    private final BookingService bookingService;

    public GetBookingByIdSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }


    @And("Rezervasyon id ile basarili bir sekilde goruntulensin")
    public void getBookingById(){
        Booking booking = bookingService.getBookingById(scenarioContext.getBookingResponse().getBookingId());
        scenarioContext.setBooking(booking);
        Assertions.assertNotNull(booking);
    }

    @Then("Goruntulenen rezervasyon bilgileri kontrol edilsin")
    public void checkBookingDetails(){
        Booking booking = scenarioContext.getBooking();
        Assertions.assertEquals("alper", booking.getFirstname());
        Assertions.assertEquals("test", booking.getLastname());
        Assertions.assertEquals(7, booking.getTotalprice());
        Assertions.assertTrue(booking.isDepositpaid());
        Assertions.assertEquals("Lunch", booking.getAdditionalneeds());
    }
}
