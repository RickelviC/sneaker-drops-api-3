package com.pluralsight.sneakerdrops.controllers;

import com.pluralsight.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrops.service.SneakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
@CrossOrigin
public class SneakerController {
    private final SneakerService service;

    public SneakerController(SneakerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sneaker> getAll(@RequestParam(required = false) Integer releaseYear,
                                @RequestParam(required = false) String brand,
                                @RequestParam(required = false) Double minPrice,
                                @RequestParam(required = false) String sort) {
        return service.search(releaseYear, brand, minPrice, sort);
    }

    @GetMapping("/{id}")
    public Sneaker getById(@PathVariable long id) {
        return service.byId(id);
    }

    @PostMapping
    public ResponseEntity<Sneaker> create(@RequestBody Sneaker sneaker) {
        Sneaker saved = service.makeSneaker(sneaker);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Sneaker update(@PathVariable long id, @RequestBody Sneaker sneaker) {
        return service.updateSneaker(id, sneaker);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteSneaker(id);
        return ResponseEntity.noContent().build();
    }
}
