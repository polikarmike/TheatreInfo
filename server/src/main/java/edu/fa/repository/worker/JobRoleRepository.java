package edu.fa.repository;

import edu.fa.model.entities.worker.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
}
