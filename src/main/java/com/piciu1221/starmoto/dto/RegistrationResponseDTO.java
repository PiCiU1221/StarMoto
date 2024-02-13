package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.User;
import lombok.Data;

@Data
public class RegistrationResponseDTO {
    private Long userId;
    private String username;
    private String email;

    public RegistrationResponseDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
