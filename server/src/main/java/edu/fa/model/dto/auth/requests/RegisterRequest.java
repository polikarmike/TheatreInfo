package edu.fa.model.dto.user.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
