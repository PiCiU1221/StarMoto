package com.piciu1221.starmoto.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piciu1221.starmoto.controller.UserController;
import com.piciu1221.starmoto.dto.ApiResponse;
import com.piciu1221.starmoto.dto.RegistrationRequest;
import com.piciu1221.starmoto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        // Create a sample registration request for testing
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the behavior of userService.existsByUsername() and userService.existsByEmail()
        when(userService.existsByUsername(registrationRequest.getUsername())).thenReturn(false);
        when(userService.existsByEmail(registrationRequest.getEmail())).thenReturn(false);

        // Mock the response from userService.registerUser()
        ApiResponse<String> successResponse = ApiResponse.customSuccess(HttpStatus.CREATED, "User registered successfully");
        when(userService.registerUser(registrationRequest)).thenReturn(successResponse);

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(successResponse)));

        // Verify that the registration method is called with the correct arguments
        verify(userService).registerUser(registrationRequest);
    }

    @Test
    public void UserController_RegisterUser_UsernameTaken_ReturnsConflictResponse() throws Exception {
        // Create a sample registration request for testing
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser"); // Assuming "existingUser" already exists
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the behavior of userService.existsByUsername() to return true, indicating the username is taken
        when(userService.existsByUsername(registrationRequest.getUsername())).thenReturn(true);

        // Mock the response from userService.registerUser()
        ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.CONFLICT, "Username is already taken");
        when(userService.registerUser(registrationRequest)).thenReturn(errorResponse);

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));

        // Verify that the registration method is called with the correct arguments
        verify(userService).registerUser(registrationRequest);
    }

    @Test
    public void UserController_RegisterUser_EmailTaken_ReturnsConflictResponse() throws Exception {
        // Create a sample registration request for testing
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("existing@example.com"); // Assuming "existing@example.com" already exists
        registrationRequest.setPassword("testPassword");

        // Mock the behavior of userService.existsByEmail() to return true, indicating the email is taken
        when(userService.existsByEmail(registrationRequest.getEmail())).thenReturn(true);

        // Mock the response from userService.registerUser()
        ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.CONFLICT, "Email is already taken");
        when(userService.registerUser(registrationRequest)).thenReturn(errorResponse);

        // Perform the registration request
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorResponse)));

        // Verify that the registration method is called with the correct arguments
        verify(userService).registerUser(registrationRequest);
    }

    @Test
    public void UserController_RegisterUser_InvalidEmailFormat_ReturnsBadRequest() throws Exception {
        // Create a sample registration request with an invalid email format
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("invalid-email");
        registrationRequest.setPassword("testPassword");

        // Convert the object to JSON
        String jsonRequest = objectMapper.writeValueAsString(registrationRequest);

        // Perform the POST request
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        // Verify that the response status is BAD_REQUEST
        resultActions.andExpect(status().isBadRequest());
    }
}