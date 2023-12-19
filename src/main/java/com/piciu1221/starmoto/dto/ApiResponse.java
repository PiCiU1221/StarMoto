package com.piciu1221.starmoto.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), null, data);
    }

    public static <T> ApiResponse<T> customSuccess(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null);
    }
}