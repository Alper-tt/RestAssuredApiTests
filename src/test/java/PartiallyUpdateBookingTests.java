import com.restassured.model.request.Booking;
import com.restassured.model.request.Credential;
import com.restassured.model.response.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PartiallyUpdateBookingTests extends BaseTest{

    @Test
    public void partiallyUpdateBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        CreateBookingResponse response = createBooking("alper", "test", 7);

        Booking updatedBooking = given()
                .contentType(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body("""
                        {"firstname":"arda",
                        "lastname":"cont"}
                        """)
                .when()
                .patch("https://restful-booker.herokuapp.com/booking/"+response.bookingId)
                .then()
                .statusCode(200)
                .extract().as(Booking.class);

        Assertions.assertEquals("arda",updatedBooking.firstname);
        Assertions.assertEquals("cont",updatedBooking.lastname);
    }
}
