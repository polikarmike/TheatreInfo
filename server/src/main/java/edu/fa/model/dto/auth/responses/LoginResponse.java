package edu.fa.model.dto.user.responses;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private String userName;
    private String userType;

    public LoginResponse(String token, String userName, String userType) {
        this.token = token;
        this.userName = userName;
        this.userType = userType;
    }
}
