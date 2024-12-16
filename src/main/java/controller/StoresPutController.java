package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
public class StoresPutController {
    @PutMapping("/{code}")
    public ResponseEntity<String> saveStore(@PathVariable int code, @RequestBody String body) {
        return new ResponseEntity<>("Store saved with code " + code, HttpStatus.CREATED);
    }
}
