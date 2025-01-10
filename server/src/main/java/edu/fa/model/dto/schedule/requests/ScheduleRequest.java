package edu.fa.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ScheduleRequest {
    private long performanceId;
    private LocalDate showDate;
    private LocalTime showTime;
    private long hallId;
    private List<TeamMemberRequest> teamMembers;
    private List<RoleAssignmentRequest> roleAssignments;
    private List<SeatStatusRequest> seatStatuses;
}
