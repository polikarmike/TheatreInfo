package edu.fa.repository;

import edu.fa.model.RoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleAssignmentRepository extends JpaRepository<RoleAssignment, Long> {
    List<RoleAssignment> findBySchedule_ScheduleId(Long scheduleId);
    void deleteBySchedule_ScheduleId(Long scheduleId);
}
