import com.restassured.model.request.Booking;
import com.restassured.model.request.BookingDates;
import com.restassured.model.request.Credential;
import com.restassured.model.response.AuthToken;
import com.restassured.model.response.CreateBookingResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;

    @BeforeAll
    static void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setAccept("application/json")
                .addFilter(new AllureRestAssured())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(2000L))
                .build();
    }

    @AfterAll
    static void generateReport() throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                "allure", "generate", "target/allure-results", "-o", "target/allure-report", "--clean"
        ).inheritIO().start();
        process.waitFor();
    }

    protected String createToken(Credential credential) {
        AuthToken authToken =
                given().spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .when()
                        .post("/auth")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthToken.class);

        assertThat(authToken, notNullValue());
        return authToken.token;
    }

    protected CreateBookingResponse createBooking(String firstname, String lastname, int totalprice) {
        CreateBookingResponse bookingResponse =
                given().spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(new Booking(firstname, lastname, totalprice, false,
                                new BookingDates("2013-02-23", "2014-10-23"), "Lunch"))
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec)
                        .statusCode(anyOf(is(200), is(201)))
                        .extract().as(CreateBookingResponse.class);

        assertThat(bookingResponse.booking.firstname, equalTo(firstname));
        assertThat(bookingResponse.booking.lastname, equalTo(lastname));
        return bookingResponse;
    }

    protected Integer createBookingId(String firstname, String lastname, int totalprice) {
        CreateBookingResponse bookingResponse = createBooking(firstname, lastname, totalprice);
        return bookingResponse.bookingId;
    }

    protected void getBookingsWithQueryParams(String firstname, String lastname) {
        given().spec(requestSpec)
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("$", not(empty()));
    }

    protected Booking getBookingById(Integer bookingId) {
        return given().spec(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().ifValidationFails()
                .extract().as(Booking.class);
    }

    protected void getBookingSchemaValidation(Integer bookingId) {
        given().spec(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-get.json"));
    }

}
