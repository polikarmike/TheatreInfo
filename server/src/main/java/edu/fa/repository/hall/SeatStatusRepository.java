package edu.fa.repository;

import edu.fa.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
    List<SeatStatus> findByScheduleId(Long scheduleId);
    void deleteByScheduleId(Long scheduleId);
}

