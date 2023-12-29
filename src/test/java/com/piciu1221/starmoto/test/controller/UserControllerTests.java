package com.piciu1221.starmoto.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piciu1221.starmoto.controller.UserController;
import com.piciu1221.starmoto.dto.ApiSuccessfulResponse;
import com.piciu1221.starmoto.dto.RegistrationRequest;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.exception.EmailTakenException;
import com.piciu1221.starmoto.exception.UsernameTakenException;
import com.piciu1221.starmoto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void UserController_RegisterUser_ReturnsSuccessResponse() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        // When everything goes right the userService method returns void
        doNothing().when(userService).registerUser(registrationRequest);

        // Expected response
        ApiSuccessfulResponse successResponse = new ApiSuccessfulResponse("User registered successfully");

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(successResponse)));
    }

    @Test
    public void UserController_RegisterUser_InvalidRequest_ReturnsBadRequestResponse() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        // Invalid data
        registrationRequest.setUsername("abc");
        registrationRequest.setEmail("abc");
        registrationRequest.setPassword("abc");

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void UserController_RegisterUser_UsernameTaken_ReturnsConflictResponse() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the userService to throw a RegistrationException
        doThrow(new UsernameTakenException("Username is already taken")).when(userService).registerUser(registrationRequest);

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("RegistrationException", "Username is already taken");

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void UserController_RegisterUser_EmailTaken_ReturnsConflictResponse() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the userService to throw a RegistrationException
        doThrow(new EmailTakenException("Email is already taken")).when(userService).registerUser(registrationRequest);

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("RegistrationException", "Email is already taken");

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void UserController_RegisterUser_InternalServerError_ReturnsInternalServerErrorResponse() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the userService to throw an unexpected exception
        doThrow(new RuntimeException("Unexpected error")).when(userService).registerUser(registrationRequest);

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred.");

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }
}