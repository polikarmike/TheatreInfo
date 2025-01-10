package edu.fa.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatStatusRequest {
    private Long seatId;
    private String seatType;
    private Double price;
    private Boolean isOccupied;
}

