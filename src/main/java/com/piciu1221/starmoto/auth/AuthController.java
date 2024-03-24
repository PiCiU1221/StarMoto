package com.piciu1221.starmoto.auth;

import com.piciu1221.starmoto.dto.LoginRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationResponseDTO;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        try {
            String token = authService.authenticate(loginRequestDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO
    ) {
        try {
            RegistrationResponseDTO savedUser = authService.register(registrationRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedUser);
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("RegistrationException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }
}