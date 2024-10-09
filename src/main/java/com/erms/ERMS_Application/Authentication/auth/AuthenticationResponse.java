package com.erms.ERMS_Application.Authentication.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("error_message")
    private String errorMessage;


    // Default constructor
    public AuthenticationResponse() {
    }

    // Parameterized constructor for successful authentication


    public AuthenticationResponse(String accessToken, String refreshToken, String errorMessage) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errorMessage = errorMessage;
    }

    // Parameterized constructor for error handling
    public AuthenticationResponse(String errorMessage) {
        this.accessToken = null; // Clear access token on error
        this.refreshToken = null; // Clear refresh token on error
        this.errorMessage = errorMessage;
    }

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
