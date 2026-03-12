package com.alok.bankingapp.dto;

public class ErrorResponse {
    private String errorCode;
    private String message;

    // Constructor
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    // Getters
    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
