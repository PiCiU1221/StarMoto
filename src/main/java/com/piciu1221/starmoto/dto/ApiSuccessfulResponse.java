package com.piciu1221.starmoto.dto;

import lombok.Data;

@Data
public class ApiSuccessfulResponse {
    private String message;

    public ApiSuccessfulResponse(String message) {
        this.message = message;
    }
}
