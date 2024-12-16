package system;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class StoresPutControllerFeature {
    public static final String STORE_JSON = "stores.json";

    @BeforeEach
    void setUp() throws IOException {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        Files.deleteIfExists(Path.of(STORE_JSON));
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

        assertThat(Files.exists(Path.of(STORE_JSON))).isTrue();
    }
}
