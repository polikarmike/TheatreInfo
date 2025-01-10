package edu.fa.model.dto.schedule;

import edu.fa.model.dto.performance.responses.PerformanceTitleListResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSummaryResponse {
    private Long scheduleId;
    private LocalDate showDate;
    private LocalTime showTime;
    private Long performanceId;
    private String performanceTitle;
}
