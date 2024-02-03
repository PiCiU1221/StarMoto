package com.piciu1221.starmoto.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piciu1221.starmoto.controller.AuthController;
import com.piciu1221.starmoto.dto.LoginRequestDTO;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

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
    public void login_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUser");
        loginRequestDTO.setPassword("testPassword");

        Authentication mockAuthentication = mock(Authentication.class);

        when(authService.authenticateUser(loginRequestDTO)).thenReturn(mockAuthentication);
        when(authService.generateToken(mockAuthentication)).thenReturn("mockToken");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDTO)));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer mockToken"));

        // Verify that the authentication and token generation methods are called with the correct arguments
        verify(authService, times(1)).authenticateUser(loginRequestDTO);
        verify(authService, times(1)).generateToken(mockAuthentication);
    }

    @Test
    public void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("invalidUser");
        loginRequestDTO.setPassword("invalidPassword");

        when(authService.authenticateUser(loginRequestDTO)).thenThrow(new AuthenticationException("Invalid credentials") {
        });

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDTO)));

        // Assert

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("AuthenticationException", "Invalid credentials");

        resultActions.andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));

        // Verify that the authentication method is called with the correct arguments
        verify(authService, times(1)).authenticateUser(loginRequestDTO);
    }
}