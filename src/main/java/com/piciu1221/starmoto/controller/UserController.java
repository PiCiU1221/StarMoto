package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.ApiSuccessfulResponse;
import com.piciu1221.starmoto.dto.RegistrationRequest;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        try {
            userService.registerUser(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiSuccessfulResponse("User registered successfully"));
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("RegistrationException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }
}
