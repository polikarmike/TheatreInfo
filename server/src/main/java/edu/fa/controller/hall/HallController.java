package edu.fa.controller;

import edu.fa.model.entities.hall.Hall;
import edu.fa.service.hall.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<Hall> getHallById(@PathVariable Long hallId) {
        Optional<Hall> hall = hallService.getHallById(hallId);
        return hall.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallService.saveHall(hall);
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long hallId) {
        hallService.deleteHall(hallId);
        return ResponseEntity.noContent().build();
    }
}
