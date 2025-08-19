import com.restassured.model.request.Booking;
import com.restassured.model.request.BookingDates;
import com.restassured.model.request.Credential;
import com.restassured.model.response.AuthToken;
import com.restassured.model.response.CreateBookingResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("Booking API")
@Feature("CRUD")
public class FirstApiTest {

    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    static Integer bookingId;
    static String token;
    static CreateBookingResponse bookingResponse;
    static AuthToken authToken;

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

 
    @Test
    @Order(1)
    @DisplayName("POST /booking → nesne oluşturma")
    @Story("Create a new booking")
    @Step("Create booking (firstname=alper, lastname=topraktepe)")
    void createBooking() {
        bookingResponse =
                given().spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(new Booking("alper", "topraktepe", 123, false,
                                new BookingDates("2013-02-23", "2014-10-23"), "Lunch"))
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec)
                        .statusCode(anyOf(is(200), is(201)))
                        .extract().as(CreateBookingResponse.class);

        bookingId = bookingResponse.bookingId;
        assertThat(bookingResponse.booking.firstname, equalTo("alper"));
        assertThat(bookingResponse.booking.lastname, equalTo("topraktepe"));
    }

    @Test
    @Order(2)
    @DisplayName("POST /auth → token oluşturma")
    @Step("Create auth token (admin/password123)")
    @Story("Create a new auth token")
    void createToken() {
        authToken =
                given().spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(new Credential("admin", "password123"))
                        .when()
                        .post("/auth")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthToken.class);

        token = authToken.token;
        assertThat(token, notNullValue());
    }

    @Test
    @Order(3)
    @DisplayName("GET /booking?firstname=&lastname= → query ile arama")
    @Step("Search bookings by firstname=alper & lastname=topraktepe")
    @Story("Get bookings with first and last name")
    void getBookingsWithQueryParams() {
        given().spec(requestSpec)
                .queryParam("firstname", "alper")
                .queryParam("lastname", "topraktepe")
                .when()
                .get("/booking")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(4)
    @DisplayName("PUT /booking/{id} → token ile güncelle")
    @Step("Update booking #{bookingId} (firstname=arda, lastname=kaya)")
    @Story("Update booking details")
    void updateBooking() {
        bookingResponse.booking =
                given().spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .cookie("token", token)
                        .pathParam("id", bookingId)
                        .body(new Booking("arda", "kaya", 123, false,
                                new BookingDates("2013-02-23", "2014-10-23"), "Lunch"))
                        .when()
                        .put("/booking/{id}")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(Booking.class);

        assertThat(bookingResponse.booking.firstname, equalTo("arda"));
        assertThat(bookingResponse.booking.lastname, equalTo("kaya"));
    }

    @Test
    @Order(5)
    @DisplayName("GET /booking/{id} → tek kayıt")
    @Step("Fetch booking #{bookingId}")
    @Story("Get bookings with id")
    void getBookingById() {
        given().spec(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().all()
                .extract().as(Booking.class);
    }

    @Test
    @Order(6)
    @DisplayName("GET /booking/{id} → JSON schema doğrulama")
    @Step("Validate JSON schema for booking #{bookingId}")
    @Story("Validate JSON schema")
    void getBookingSchemaValidation() {
        given().spec(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-get.json"));
    }

    @Test
    @Order(7)
    @DisplayName("DELETE /booking/{id} → sil ve 404 doğrula")
    @Step("Delete booking #{bookingId} and verify 404 on read")
    @Story("Delete booking and check it")
    void deleteBooking() {
        given().spec(requestSpec)
                .cookie("token", token)
                .pathParam("id", bookingId)
                .when()
                .delete("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(anyOf(is(200), is(201), is(204)));

        given().spec(requestSpec)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(404);
    }
}
