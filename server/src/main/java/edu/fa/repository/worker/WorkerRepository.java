package edu.fa.repository;

import edu.fa.model.dto.worker.responses.WorkerListResponse;
import edu.fa.model.entities.worker.Worker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("SELECT new edu.fa.model.dto.worker.responses.WorkerListResponse(w.workerId, w.firstName, w.lastName, w.photoUrl, r) " +
            "FROM Worker w JOIN w.role r")
    List<WorkerListResponse> findAllBrief();

}
