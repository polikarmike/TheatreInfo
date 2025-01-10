package edu.fa.service;

import edu.fa.model.Performance;
import edu.fa.model.Schedule;
import edu.fa.model.Hall;

import edu.fa.repository.ScheduleRepository;
import edu.fa.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public Schedule createSchedule(Long performanceId, LocalDate showDate, LocalTime showTime, Long hallId) {
        Schedule schedule = new Schedule();
        Performance performance = performanceService.getPerformance(performanceId);
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        schedule.setPerformance(performance);
        schedule.setShowDate(showDate);
        schedule.setShowTime(showTime);
        schedule.setHall(hall);

        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Long performanceId, LocalDate showDate, LocalTime showTime, Long hallId) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Performance performance = performanceService.getPerformance(performanceId);
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        existingSchedule.setPerformance(performance);
        existingSchedule.setShowDate(showDate);
        existingSchedule.setShowTime(showTime);
        existingSchedule.setHall(hall);

        return scheduleRepository.save(existingSchedule);
    }


    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }
}
