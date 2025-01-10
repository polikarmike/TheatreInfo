package edu.fa.repository;

import edu.fa.model.PerformanceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRoleRepository extends JpaRepository<PerformanceRole, Long> {
    List<PerformanceRole> findByPerformance_PerformanceId(Long performanceId);
}
