package com.alok.bankingapp.dto;

import java.util.Map;

public class ValidationErrorResponse {
    private String errorCode;
    private String message;
    private Map<String, String> errors;

    public ValidationErrorResponse(String errorCode, String message, Map<String, String> errors) {
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
