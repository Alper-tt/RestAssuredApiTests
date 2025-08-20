import com.restassured.model.request.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetBookingByIdTests extends  BaseTest {

    @Test
    public void getBookingByIdTest(){
        int bookingId = createBookingId("alper", "test", 7);
        Booking response = given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().body().as(Booking.class);

        Assertions.assertEquals("alper",response.firstname);
        Assertions.assertEquals("test",response.lastname);
        Assertions.assertEquals(7,response.totalprice);
    }
}
