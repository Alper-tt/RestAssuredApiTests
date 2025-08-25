package com.booking.steps;

import com.booking.context.ScenarioContext;
import com.booking.models.response.CreateBookingResponse;
import com.booking.services.BookingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CommonSteps {
    private final ScenarioContext scenarioContext;
    BookingService bookingService;

    public CommonSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.bookingService = new BookingService();
    }

    @Given("Kullanici rezervasyon icin giris bilgilerini girsin")
    public void createToken(){
        scenarioContext.setToken(bookingService.generateToken());
    }

    @When("Kullanici rezervasyonu olustursun")
    public void createBooking(){
        scenarioContext.setBookingResponse(bookingService.createBooking("alper", "test", 7));
    }

    @Then("Rezervasyon basarili sekilde olusturulsun")
    public void isBookingCreated(){
        CreateBookingResponse bookingResponse = scenarioContext.getBookingResponse();
        Assertions.assertTrue(bookingService.isBookingPresent(bookingResponse.getBookingId()));
        Assertions.assertEquals("alper", bookingResponse.getBooking().getFirstname());
        Assertions.assertEquals("test", bookingResponse.getBooking().getLastname());
        Assertions.assertEquals(7, bookingResponse.getBooking().getTotalprice());
    }
}