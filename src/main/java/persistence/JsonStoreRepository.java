package persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Store;
import domain.StoreRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonStoreRepository implements StoreRepository {
    private final String jsonFilePath;
    private final Gson gson;

    public JsonStoreRepository(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .create();
    }

    public void upsert(Store store) {
        List<Store> stores;
        try {
            if (Files.exists(Paths.get(jsonFilePath))) {
                String jsonString = Files.readString(Paths.get(jsonFilePath));
                if (jsonString.trim().isEmpty()) {
                    stores = new ArrayList<>();
                } else {
                    Type type = new TypeToken<List<Store>>() { }.getType();
                    stores = gson.fromJson(jsonString, type);
                }
            } else {
                stores = new ArrayList<>();
            }

            stores.add(store);
            String updatedJsonString = gson.toJson(stores);
            Files.writeString(Paths.get(jsonFilePath), updatedJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<Store> searchAll() {
        if (!Files.exists(Paths.get(jsonFilePath))) {
            return Stream.empty();
        }

        try {
            var jsonString = Files.readString(Paths.get(jsonFilePath));
            Type type = new TypeToken<List<Store>>() { }.getType();
            List<Store> stores = gson.fromJson(jsonString, type);

            return stores.stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
