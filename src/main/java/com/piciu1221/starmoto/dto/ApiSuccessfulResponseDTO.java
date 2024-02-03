package com.piciu1221.starmoto.dto;

import lombok.Data;

@Data
public class ApiSuccessfulResponseDTO {
    private String message;

    public ApiSuccessfulResponseDTO(String message) {
        this.message = message;
    }
}
