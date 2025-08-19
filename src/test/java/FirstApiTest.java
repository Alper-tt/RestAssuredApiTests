
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FirstApiTest {

    static RequestSpecification base;
    static Integer bookingId;
    static String token;

    @BeforeAll
    static void setup() {
        base = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .accept("application/json")
                .log().all();
    }

    @Test
    @Order(1)
    @DisplayName("POST /booking -> nesne oluşturma")
    public void createBooking() {
        bookingId = given().spec(base)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "firstname": "alper",
                          "lastname": "topraktepe",
                          "totalprice": 111,
                          "depositpaid": true,
                          "bookingdates": { "checkin": "2013-02-23", "checkout": "2014-10-23" },
                          "additionalneeds": "Breakfast"
                        }
                        """)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");
    }

    @Test
    @Order(2)
    @DisplayName("POST /auth -> token oluşturma")
    public void createToken() {
        token = given().spec(base)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "username":"admin",
                          "password": "password123"
                        }
                        """)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

    @Test
    @Order(3)
    @DisplayName("GET /booking?firstname=&lastname= → queryParam ile arama")
    public void getBookingsWithQueryParams() {
        given().spec(base)
                .queryParam("firstname", "alper")
                .queryParam("lastname", "topraktepe")
                .when()
                .get("/booking")
                .then()
                .log().all()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(4)
    @DisplayName("PUT /booking/{id} token ile güncelle")
    public void updateBooking() {
        given().spec(base)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .pathParam("id", bookingId)
                .body("""
                        {
                          "firstname": "arda",
                          "lastname": "kaya",
                          "totalprice": 111,
                          "depositpaid": true,
                          "bookingdates": { "checkin": "2013-02-23", "checkout": "2014-10-23" },
                          "additionalneeds": "Breakfast"
                        }
                        """)
                .when()
                .put("/booking/{id}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("arda"))
                .body("lastname", equalTo("kaya"));
    }

    @Test
    @Order(5)
    @DisplayName("GET /booking/{id} → pathParam ile tek kayıt")
    public void getBookingById() {
        given().spec(base)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", not(empty()));
    }

    @Test
    @Order(6)
    @DisplayName("DELETE /booking/{id} -> sil ve doğrula")
    public void deleteBooking() {
        given().spec(base)
                .pathParam("id", bookingId)
                .cookie("token", token)
                .when()
                .delete("/booking/{id}")
                .then()
                .statusCode(201);

        given().spec(base)
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(404);
    }
}
