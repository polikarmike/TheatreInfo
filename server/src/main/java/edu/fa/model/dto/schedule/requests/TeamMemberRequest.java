package edu.fa.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamMemberRequest {
    private Long workerId;
    private Long roleId;
}