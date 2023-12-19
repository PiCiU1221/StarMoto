package com.piciu1221.starmoto.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piciu1221.starmoto.controller.AuthController;
import com.piciu1221.starmoto.dto.LoginRequest;
import com.piciu1221.starmoto.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void AuthController_LoginUser_ReturnsToken() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
        Authentication mockAuthentication = mock(Authentication.class);

        when(authService.authenticateUser(loginRequest)).thenReturn(mockAuthentication);
        when(authService.generateToken(mockAuthentication)).thenReturn("mockToken");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer mockToken"));

        // Verify that the authentication and token generation methods are called with the correct arguments
        verify(authService, times(1)).authenticateUser(loginRequest);
        verify(authService, times(1)).generateToken(mockAuthentication);
    }

    @Test
    public void AuthController_LoginUser_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalidUser", "invalidPassword");

        when(authService.authenticateUser(loginRequest)).thenThrow(new AuthenticationException("Invalid credentials") {
        });

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // Assert
        resultActions.andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));

        // Verify that the authentication method is called with the correct arguments
        verify(authService, times(1)).authenticateUser(loginRequest);
    }
}