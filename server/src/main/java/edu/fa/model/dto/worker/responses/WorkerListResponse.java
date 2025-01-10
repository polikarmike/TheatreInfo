package edu.fa.model.dto.worker.requests;

import edu.fa.model.entities.worker.JobRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerListResponse {
    private Long workerId;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private JobRole role;
}
