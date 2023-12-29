package com.piciu1221.starmoto.test.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
        registrationRequest.setUsername("newUser");
        registrationRequest.setEmail("new@example.com");
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
        registrationRequest.setEmail("new@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the repository behavior to simulate an existing user
        when(userRepository.existsByUsername(any())).thenReturn(true);

        // Act and Assert
        assertThrows(UsernameTakenException.class, () -> userService.registerUser(registrationRequest));

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("newUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the repository behavior to simulate an existing email
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act and Assert
        assertThrows(EmailTakenException.class, () -> userService.registerUser(registrationRequest));

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_UsernameAndEmailAlreadyExist() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser");
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("testPassword");

        // Mock the repository behavior to simulate both existing username and email
        when(userRepository.existsByUsername(any())).thenReturn(true);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act and Assert
        assertThrows(UsernameTakenException.class, () -> userService.registerUser(registrationRequest));

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void testValidateUserRegistration_UsernameTooShort() {
        // Arrange user with a username that is too short
        User user = new User();
        user.setUsername("abc");  // Less than the required minimum of 4 characters
        user.setEmail("test@email.com");
        user.setPassword("testPassword");

        // Mock the validation result
        ConstraintViolation<User> violation = mock(ConstraintViolation.class);
        Set<ConstraintViolation<User>> violationsSet = Collections.singleton(violation);
        when(violation.getMessage()).thenReturn("Username must be at least 4 characters");
        when(validator.validate(user)).thenReturn(violationsSet);

        // Test the case when there is a validation error due to username being too short
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userService.validateUserRegistration(user));

        // Verify that the userRepository methods are called as expected
        verify(validator, times(1)).validate(user);

        // Verify that the exception contains the expected error message
        assertThat(exception.getConstraintViolations()).hasSize(1);
        assertThat(exception.getMessage()).contains("Username must be at least 4 characters");
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