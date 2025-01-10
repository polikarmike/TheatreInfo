package edu.fa.controller;

import edu.fa.model.dto.hall.responses.SeatStatusResponse;
import edu.fa.model.entities.hall.SeatStatus;
import edu.fa.service.hall.SeatStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat-status")
public class SeatStatusController {

    private final SeatStatusService seatStatusService;

    @GetMapping("/schedule/{scheduleId}")
    public List<SeatStatusResponse> getSeatStatusByScheduleId(@PathVariable int scheduleId) {
        List<SeatStatus> seatStatusList = seatStatusService.getSeatStatusByScheduleId(scheduleId);
        return seatStatusList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SeatStatusResponse getSeatStatusById(@PathVariable Long id) {
        SeatStatus seatStatus = seatStatusService.getSeatStatusById(id);
        return convertToDto(seatStatus);
    }

    @PostMapping(consumes = "application/json")
    public SeatStatusResponse createSeatStatus(@RequestBody SeatStatus seatStatus) {
        SeatStatus savedSeatStatus = seatStatusService.saveSeatStatus(seatStatus);
        return convertToDto(savedSeatStatus);
    }

    @PutMapping(consumes = "application/json")
    public List<SeatStatusResponse> updateSeatStatus(@RequestBody List<SeatStatus> seatStatusList) {
        List<SeatStatus> savedSeatStatusList = seatStatusService.saveAllSeatStatus(seatStatusList);
        return savedSeatStatusList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatStatus(@PathVariable Long id) {
        seatStatusService.deleteSeatStatus(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mark-occupied")
    public ResponseEntity<Void> markSeatsAsOccupied(@RequestBody List<Long> seatIds) {
        seatStatusService.markSeatsAsOccupied(seatIds);
        return ResponseEntity.ok().build();
    }

    private SeatStatusResponse convertToDto(SeatStatus seatStatus) {
        SeatStatusResponse seatDTO = new SeatStatusResponse();
        seatDTO.setStatusId(seatStatus.getStatusId().intValue());
        seatDTO.setSeatId(seatStatus.getSeat().getSeatId().intValue());
        seatDTO.setRowNumber(seatStatus.getSeat().getRowNumber());
        seatDTO.setSeatNumber(seatStatus.getSeat().getSeatNumber());
        seatDTO.setOccupied(seatStatus.getIsOccupied());
        seatDTO.setSeatType(seatStatus.getSeatType());
        seatDTO.setPrice(seatStatus.getPrice());
        return seatDTO;
    }
}
