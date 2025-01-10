package edu.fa.controller;

import edu.fa.model.entities.hall.Seat;
import edu.fa.service.hall.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getSeatsByHallId(@RequestParam Long hallId) {
        System.out.println("Fetching seats for hallId: " + hallId);
        return seatService.getSeatsByHallId(hallId); }


//    @GetMapping
//    public List<Seat> getSeatsByHallId(@RequestParam Long hallId) {
//        return seatService.getSeatsByHallId(hallId);
//    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }

    @PostMapping(consumes = "application/json")
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @PutMapping(consumes = "application/json")
    public List<Seat> updateSeats(@RequestBody List<Seat> seats) {
        return seatService.saveAllSeats(seats);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
