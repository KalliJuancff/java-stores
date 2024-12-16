package system;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class StoresPutControllerShould {
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void create_a_new_store() {
        String requestBody = """
                {
                    "storeCode": "4321",
                    "storeName": "Store #4321",
                    "storeOpeningDate": "",
                    "storeClosingDate": "",
                    "storeExpectedOpeningDate": "2025/2026"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
        .when()
                .put("/stores/4321")
        .then()
                .statusCode(201);
    }
}
