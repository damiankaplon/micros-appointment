package pl.damiankaplon.endToEnd;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.damiankaplon.service.SecurityService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

//To run this tests make sure you have run make dev-env-clean, then make dev-env-up

@QuarkusTest
public class AccountResourceTests {

    @Test
    public void mockTest() {
        Assertions.assertEquals(2, 2);
    }
//    @Test
//    public void registerTest() {
//        var registerRequest = new SecurityService.Registration
//                ("testEmail@email.com", "pas@!wrd", "name", "surname");
//
//        given()
//                .header(new Header("Content-Type", "application/json"))
//                .body(registerRequest)
//                .when().post("/api/v1/security/account")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    // This test uses data initialized in src/main/docker/mongo-init.js
//    public void loginTest() {
//        var credentials = new SecurityService.Credentials("email", "password");
//
//        given()
//                .header(new Header("Content-Type", "application/json"))
//                .body(credentials)
//                .when().post("/api/v1/security/account/login")
//                .then()
//                .statusCode(200)
//                .body("token", notNullValue())
//                .body("refreshToken", notNullValue())
//                .body("userId", notNullValue());
//    }
//
//    @Test
//    // This test uses data initialized in src/main/docker/mongo-init.js
//    public void refreshToken() {
//        var credentials = new SecurityService.Credentials("email", "password");
//        RequestSpecification loginRequest = RestAssured.given();
//        loginRequest.header(new Header("Content-Type", "application/json"));
//        loginRequest.body(credentials);
//        Response response = loginRequest.post("/api/v1/security/account/login");
//        String token = response.getBody().jsonPath().get("token.token");
//        String refreshToken = response.getBody().jsonPath().get("refreshToken.token");
//
//        given()
//                .header(new Header("Content-Type", "application/json"))
//                .header(new Header("Authorization", "Bearer " + token))
//                .body(new SecurityService.BearerToken(refreshToken))
//                .when().post("/api/v1/security/account/token/refresh")
//                .then()
//                .statusCode(200)
//                .body("token", notNullValue());
//    }
}
