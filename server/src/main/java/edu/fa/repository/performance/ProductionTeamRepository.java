package edu.fa.repository;

import edu.fa.model.ProductionTeam;
import edu.fa.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionTeamRepository extends JpaRepository<ProductionTeam, Long> {
        List<ProductionTeam> findBySchedule(Schedule schedule);
        void deleteBySchedule_ScheduleId(Long scheduleId);
}
