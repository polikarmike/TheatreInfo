package edu.fa.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private List<Long> seatStatusIds;
}
