package edu.fa.service;


import edu.fa.model.Seat;
import edu.fa.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getSeatsByHallId(Long hallId) {
        return seatRepository.findByHallId(hallId);
    }

    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        seatRepository.deleteById(seatId);
    }

    public List<Seat> saveAllSeats(List<Seat> seats) {
        return seatRepository.saveAll(seats);
    }
}

