package com.lyamada;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class APIResourceTest {
    @Test
    public void testDownloadMarvelDB() {
        given()
                .when().get("/api/download")
                .then()
                .statusCode(200);
    }
}
