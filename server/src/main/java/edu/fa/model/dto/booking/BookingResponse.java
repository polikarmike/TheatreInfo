package edu.fa.model.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long scheduleId;
    private String performanceTitle;
    private String showDate;
    private String showTime;
    private String seatInfo;
}

