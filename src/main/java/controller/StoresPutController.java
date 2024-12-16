package controller;

import application.StoreSaver;
import domain.StoreSaverRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoresPutController {
    private final StoreSaver storeSaver;

    public StoresPutController(StoreSaver storeSaver) {
        this.storeSaver = storeSaver;
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> saveStore(@PathVariable int code, @RequestBody String body) {
        StoreSaverRequest request = new StoreSaverRequest(
                7777,
                "Store 7777",
                "",
                "",
                UUID.randomUUID().toString());
        storeSaver.save(request);

        return new ResponseEntity<>("Store saved with code " + code, HttpStatus.CREATED);
    }
}
