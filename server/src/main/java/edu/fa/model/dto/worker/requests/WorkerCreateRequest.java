package edu.fa.model.dto.worker.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerCreateRequest {
    private String firstName;
    private String lastName;
    private String biography;
    private Long roleId;
    private MultipartFile photo;
}

