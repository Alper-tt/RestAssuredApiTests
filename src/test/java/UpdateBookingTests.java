import com.restassured.model.request.Booking;
import com.restassured.model.request.Credential;
import com.restassured.model.response.AuthToken;
import com.restassured.model.response.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateBookingTests extends BaseTest{

    @Test
    public void updateBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        CreateBookingResponse response = createBooking("alper", "test", 7);
        Booking newBooking = response.booking;
        newBooking.firstname = "arda";
        newBooking.lastname = "cont";
        newBooking.totalprice = 5;

        Booking updatedBooking = given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .body(newBooking)
                .when()
                .put("https://restful-booker.herokuapp.com/booking/"+response.bookingId)
                .then()
                .statusCode(200)
                .extract().body().as(Booking.class);

        Assertions.assertEquals("arda",updatedBooking.firstname);
        Assertions.assertEquals("cont",updatedBooking.lastname);
        Assertions.assertEquals(5,updatedBooking.totalprice);
    }
}
