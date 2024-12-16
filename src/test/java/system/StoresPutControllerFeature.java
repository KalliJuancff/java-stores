package system;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Store;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public void create_a_new_store() throws IOException {
        String requestBody = """
                {
                    "storeCode": "4321",
                    "storeName": "Store #4321",
                    "storeOpeningDate": "2003/12/23",
                    "storeClosingDate": "2003/12/25",
                    "storeExpectedOpeningDate": ""
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
        .when()
                .put("/stores/4321")
        .then()
                .statusCode(201);

        ensureThatStoreWasPersistedWithValues("4321", "Store #4321", "2003-12-23", "2003-12-25", "");
    }

    private void ensureThatStoreWasPersistedWithValues(
            String code,
            String name,
            String openingDate,
            String closingDate,
            String expectedOpeningDate
    ) throws IOException {
        assertThat(Files.exists(Path.of(STORE_JSON))).isTrue();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .create();
        var jsonString = Files.readString(Paths.get(STORE_JSON));
        Type type = new TypeToken<List<Store>>() { }.getType();
        List<Store> stores = gson.fromJson(jsonString, type);
        var store = stores.get(0);

        assertThat(store.code()).isEqualTo(Integer.parseInt(code));
        assertThat(store.name()).isEqualTo(name);
        assertThat(store.openingDate().get()).isEqualTo(LocalDate.parse(openingDate));
        assertThat(store.closingDate().get()).isEqualTo(LocalDate.parse(closingDate));
        assertThat(store.expectedOpeningDate()).isEqualTo(expectedOpeningDate);
    }
}
