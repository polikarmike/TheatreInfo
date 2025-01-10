package edu.fa.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleAssignmentRequest {
    private Long roleId;
    private Long workerId;
}
