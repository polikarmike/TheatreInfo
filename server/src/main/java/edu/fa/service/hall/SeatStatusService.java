package edu.fa.service;

import edu.fa.model.SeatStatus;
import edu.fa.repository.SeatStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SeatStatusService {

    @Autowired
    private SeatStatusRepository seatStatusRepository;

    public List<SeatStatus> getSeatStatusByScheduleId(Long scheduleId) {
        return seatStatusRepository.findByScheduleId(scheduleId);
    }

    public SeatStatus getSeatStatusById(Long statusId) {
        return seatStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Seat status not found"));
    }

    public SeatStatus saveSeatStatus(SeatStatus seatStatus) {
        return seatStatusRepository.save(seatStatus);
    }

    public void deleteSeatStatus(Long statusId) {
        seatStatusRepository.deleteById(statusId);
    }

    @Transactional
    public void deleteByScheduleId(Long scheduleId) {
        seatStatusRepository.deleteByScheduleId(scheduleId);
    }

    public List<SeatStatus> saveAllSeatStatus(List<SeatStatus> seatStatus) {
        return seatStatusRepository.saveAll(seatStatus);
    }

    public void markSeatsAsOccupied(List<Long> seatIds) {
        List<SeatStatus> seatStatuses = seatStatusRepository.findAllById(seatIds);
        for (SeatStatus seatStatus : seatStatuses) {
            seatStatus.setIsOccupied(true);
        }
        System.out.println(seatStatuses);
        seatStatusRepository.saveAll(seatStatuses);
    }
}
