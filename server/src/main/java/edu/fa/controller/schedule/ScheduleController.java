package edu.fa.controller;

import edu.fa.model.dto.schedule.requests.ScheduleRequest;
import edu.fa.model.dto.schedule.responses.ScheduleSummaryResponse;
import edu.fa.model.entities.worker.RoleAssignment;
import edu.fa.model.entities.schedule.Schedule;
import edu.fa.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/summaries") public ResponseEntity<List<ScheduleSummaryResponse>> getScheduleSummaries() {
        List<ScheduleSummaryResponse> scheduleSummaries = scheduleService.getScheduleSummaries();
        return ResponseEntity.ok(scheduleSummaries); }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequest));
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, scheduleRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
