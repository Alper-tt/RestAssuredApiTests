import com.restassured.model.response.CreateBookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookingTests(){
        CreateBookingResponse response = createBooking("alper", "test", 7);
        Assertions.assertEquals("alper", response.booking.firstname);
        Assertions.assertEquals("test", response.booking.lastname);
        Assertions.assertEquals(7, response.booking.totalprice);
    }

}
