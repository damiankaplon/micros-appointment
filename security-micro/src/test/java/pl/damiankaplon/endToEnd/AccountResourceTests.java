package pl.damiankaplon.endToEnd;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;
import pl.damiankaplon.service.SecurityService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@QuarkusTest
public class AccountResourceTests {

    //Many tests will fail if this one will not success cuz they use user which should be registered in this one
    @Test
    public void registerTest() {
        JSONObject body = new JSONObject();
        body.put("email", "rand@gmail.com");
        body.put("password", "SomeP@13assword");
        body.put("name", "name");
        body.put("surname", "surname");

        given()
                .header(new Header("Content-Type", "application/json"))
                .body(body.toJSONString())
                .when().post("/api/v1/security/account")
                .then()
                .statusCode(200);
    }

    // This test will fail if user won't successfully register in previous test
    @Test
    public void loginTest() {
        JSONObject body = new JSONObject();
        body.put("login", "random@gmail.com");
        body.put("password", "SomeP@13assword");

        given()
                .header(new Header("Content-Type", "application/json"))
                .body(body.toJSONString())
                .when().post("/api/v1/security/account/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    public void refreshToken() {
        JSONObject credentials = new JSONObject();
        credentials.put("login", "random@gmail.com");
        credentials.put("password", "SomeP@13assword");
        RequestSpecification loginRequest = RestAssured.given();
        loginRequest.header(new Header("Content-Type", "application/json"));
        loginRequest.body(credentials.toJSONString());
        Response response = loginRequest.post("/api/v1/security/account/login");
        String token = response.getBody().jsonPath().get("token.token");
        String refreshToken = response.getBody().jsonPath().get("refreshToken.token");

        given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Authorization", "Bearer " + token))
                .body(new SecurityService.BearerToken(refreshToken))
                .when().post("/api/v1/security/account/token/refresh")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
