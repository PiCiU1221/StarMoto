package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@PasswordMatches
@Data
public class RegistrationRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 4, message = "Username must be at least 4 characters")
    @Size(max = 20, message = "Username can't be longer than 20 characters")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Size(min = 5, message = "Email must be at least 5 characters")
    @Size(max = 100, message = "Email can't be longer than 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Size(max = 254, message = "Password can't be longer than 254 characters")
    private String password;

    @NotBlank(message = "Matching password is required")
    @Size(min = 8, message = "Matching password must be at least 8 characters")
    @Size(max = 254, message = "Matching password can't be longer than 254 characters")
    private String matchingPassword;
}