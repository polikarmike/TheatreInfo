package edu.fa.service;


import edu.fa.model.Hall;
import edu.fa.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepository;

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Optional<Hall> getHallById(Long hallId) {
        return hallRepository.findById(hallId);
    }

    public Hall saveHall(Hall hall) {
        return hallRepository.save(hall);
    }

    public void deleteHall(Long hallId) {
        hallRepository.deleteById(hallId);
    }
}

