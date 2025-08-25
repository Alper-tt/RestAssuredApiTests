package com.booking.services;

import com.booking.models.request.Credential;
import com.booking.models.response.AuthToken;
import io.restassured.http.ContentType;

import static com.booking.hooks.Hooks.requestSpec;
import static com.booking.hooks.Hooks.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class BaseTest {

    public String generateToken(){
        Credential credential = new Credential("admin","password123");
        AuthToken authToken =
                given()
                        .spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .when()
                        .post("https://restful-booker.herokuapp.com/auth")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthToken.class);

        assertThat(authToken, notNullValue());
        return authToken.getToken();
    }
}
