package controller;

import application.StoreSaver;
import com.google.gson.Gson;
import domain.StoreSaverRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/stores")
public class StoresPutController {
    private final StoreSaver storeSaver;

    public StoresPutController(StoreSaver storeSaver) {
        this.storeSaver = storeSaver;
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> saveStore(@PathVariable int code, @RequestBody String body) {
        Gson gson = new Gson();
        StoreSaverRequest request = gson.fromJson(body, StoreSaverRequest.class);

        try {
            storeSaver.save(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(getStringBodyWhenSuccessfully());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(getStringBodyWhenFailure(e));
        }
    }

    private static String getStringBodyWhenSuccessfully() {
        return "{" +
                "\"ok\": \"true\"," +
                "\"message\": \"Store created successfully\"" +
                "}";
    }

    private static String getStringBodyWhenFailure(Exception e) {
        return "{" +
                "\"ok\": \"false\"," +
                "\"message\": \"" + e.getMessage() + "\"" +
                "}";
    }
}
