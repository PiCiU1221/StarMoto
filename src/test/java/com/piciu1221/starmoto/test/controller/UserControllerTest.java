package com.piciu1221.starmoto.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piciu1221.starmoto.controller.UserController;
import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    /*
    @Test
    public void registerUser_ValidRequest_ReturnsSuccessResponse() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("testUser");
        registrationRequestDTO.setEmail("test@example.com");
        registrationRequestDTO.setPassword("testPassword");

        User returnedUser = new User();
        returnedUser.setUserId(1L);
        returnedUser.setUsername(registrationRequestDTO.getUsername());
        returnedUser.setEmail(registrationRequestDTO.getEmail());
        returnedUser.setCreatedAt(LocalDateTime.now());
        returnedUser.setUpdatedAt(null);
        returnedUser.setEnabled(true);

        when(userService.registerUser(any(RegistrationRequestDTO.class))).thenReturn(returnedUser);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(registrationRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username", is(registrationRequestDTO.getUsername())))
                .andExpect(jsonPath("$.email", is(registrationRequestDTO.getEmail())))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isEmpty())
                .andExpect(jsonPath("$.enabled", is(true)));
    }
    */

    @Test
    public void registerUser_InvalidRequest_ReturnsBadRequestResponse() throws Exception {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();

        // Arrange invalid data
        registrationRequestDTO.setUsername("abc");
        registrationRequestDTO.setEmail("abc");
        registrationRequestDTO.setPassword("abc");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequestDTO)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void registerUser_UsernameTaken_ReturnsConflictResponse() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("existingUser");
        registrationRequestDTO.setEmail("test@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the userService to throw a RegistrationException
        doThrow(new RegistrationException("Username is already taken")).when(userService).registerUser(registrationRequestDTO);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequestDTO)));

        // Assert

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("RegistrationException", "Username is already taken");

        resultActions.andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void registerUser_EmailTaken_ReturnsConflictResponse() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("testUser");
        registrationRequestDTO.setEmail("existing@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the userService to throw a RegistrationException
        doThrow(new RegistrationException("Email is already taken")).when(userService).registerUser(registrationRequestDTO);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequestDTO)));

        // Assert

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("RegistrationException", "Email is already taken");

        resultActions.andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void registerUser_InternalServerError_ReturnsInternalServerErrorResponse() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("testUser");
        registrationRequestDTO.setEmail("test@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the userService to throw an unexpected exception
        doThrow(new RuntimeException("Unexpected error")).when(userService).registerUser(registrationRequestDTO);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequestDTO)));

        // Assert

        // Expected response
        ApiErrorResponse errorResponse = new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred.");

        resultActions.andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));
    }
}