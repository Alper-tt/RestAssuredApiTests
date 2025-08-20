import com.restassured.model.request.Credential;
import com.restassured.model.response.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteBookingTests extends BaseTest{

    @Test
    public void deleteBookingTest(){
        String token = createToken(new Credential("admin", "password123"));

        Integer bookingId = createBookingId("alper", "topraktepe", 7);

        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/"+bookingId)
                .then()
                .statusCode(201);

        //silindiğinden emin ol
        given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/"+bookingId)
                .then()
                .statusCode(404);
    }
}
