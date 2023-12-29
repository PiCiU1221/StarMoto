package com.piciu1221.starmoto.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ApiErrorResponse {
    private ErrorResponseData error;

    public ApiErrorResponse(String type, String message, Map<String, String> details) {
        this.error = new ErrorResponseData(type, message, details);
    }

    // Constructor without details
    public ApiErrorResponse(String type, String message) {
        this.error = new ErrorResponseData(type, message);
    }
}