package pl.damiankaplon


import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.matcher.ResponseAwareMatcher
import io.restassured.response.Response
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import java.util.regex.Pattern

@QuarkusTest
class EndpointsTests {
    @Test
    fun `successful call for token with valid credentials`() {
        given()
            .contentType(ContentType.JSON)
            .body(mapOf("login" to "email@email.com", "password" to "password"))
            .`when`()
            .post("/api/security/token")
            .then()
            .statusCode(200)
            .body("token",  not(emptyOrNullString()))
    }



}