package com.piciu1221.starmoto.test.service;

import com.piciu1221.starmoto.dto.ApiResponse;
import com.piciu1221.starmoto.dto.RegistrationRequest;
import com.piciu1221.starmoto.exception.EmailTakenException;
import com.piciu1221.starmoto.exception.UsernameTakenException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LocalValidatorFactoryBean validator;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testUser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("testPassword");

        User mockUser = new User();
        mockUser.setUsername(registrationRequest.getUsername());
        mockUser.setEmail(registrationRequest.getEmail());
        mockUser.setPassword(registrationRequest.getPassword());

        // Mock the repository behavior
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(mockUser);

        // Mock the password encoder
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");

        // Act
        userService.registerUser(registrationRequest);

        // Assert
        // Verify that the repository's save method was called with the correct user
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("existingPassword");

        // Mock the repository behavior to simulate an existing user
        when(userRepository.existsByUsername(any())).thenReturn(true);

        // Act
        ApiResponse<String> response = userService.registerUser(registrationRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        assertEquals("Username is already taken", response.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("newUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("newPassword");

        // Mock the repository behavior to simulate an existing email
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act
        ApiResponse<String> response = userService.registerUser(registrationRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        assertEquals("Email is already taken", response.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_UsernameAndEmailAlreadyExist() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("newPassword");

        // Mock the repository behavior to simulate both existing username and email
        when(userRepository.existsByUsername(any())).thenReturn(true);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act
        ApiResponse<String> response = userService.registerUser(registrationRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        assertEquals("Username is already taken", response.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testValidateUserRegistration() {
        // Mocking a user with an existing username
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@email.com");
        existingUser.setPassword("newPassword");
        when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);

        // Mocking a user with an existing email
        User newUser = new User();
        newUser.setUsername("existingUser2");
        newUser.setEmail("existing@email.com");
        newUser.setPassword("newPassword");
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);

        // Mocking a user with invalid data
        User invalidUser = new User();
        invalidUser.setUsername("newUser");
        invalidUser.setEmail("existing@email.com");
        invalidUser.setPassword("newPassword");

        // Mocking a validation error
        ConstraintViolation<User> violation = mock(ConstraintViolation.class);
        Set<ConstraintViolation<User>> violationsSet = Collections.singleton(violation);
        when(violation.getMessage()).thenReturn("Validation error");
        when(validator.validate(invalidUser)).thenReturn(violationsSet);

        // Test the case when the username is already taken
        assertThrows(UsernameTakenException.class, () -> userService.validateUserRegistration(existingUser));

        // Test the case when the email is already taken
        assertThrows(EmailTakenException.class, () -> userService.validateUserRegistration(newUser));

        // Test the case when there is a validation error
        assertThrows(ConstraintViolationException.class, () -> userService.validateUserRegistration(invalidUser));

        // Verify that the userRepository methods are called as expected
        verify(userRepository, times(1)).existsByUsername(existingUser.getUsername());
        verify(userRepository, times(1)).existsByEmail(newUser.getEmail());
        verify(validator, times(1)).validate(invalidUser);
    }

    /*
    @Test
    void testRegisterUser_InvalidData() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("sho");  // Violates @Size(min = 4) constraint
        registrationRequest.setEmail("invalid-email");  // Violates @Email constraint
        registrationRequest.setPassword("weak");  // Violates @Size(min = 8) constraint

        // Act
        try {
            userService.registerUser(registrationRequest);
            // If no exception is thrown, fail the test
            fail("Expected ConstraintViolationException, but no exception was thrown");
        } catch (ConstraintViolationException e) {
            // Assert that the caught exception is an instance of ConstraintViolationException
            assertTrue(e.getMessage().contains("User registration failed due to validation errors"));
        }

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }
    */
}