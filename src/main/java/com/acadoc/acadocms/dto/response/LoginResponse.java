package com.acadoc.acadocms.dto.response;

public class LoginResponse {
    private String token;
    private String message;

    public LoginResponse(String token){
        this.token=token;
        this.message="Login successful";
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
}
