package com.piciu1221.starmoto.test.service;

import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationResponseDTO;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

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
    void registerUser_SuccessfulRegistration_ReturnsSavedUser() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("newUser");
        registrationRequestDTO.setEmail("new@example.com");
        registrationRequestDTO.setPassword("testPassword");

        User mockUser = new User();
        mockUser.setUsername(registrationRequestDTO.getUsername());
        mockUser.setEmail(registrationRequestDTO.getEmail());
        mockUser.setPassword(registrationRequestDTO.getPassword());

        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(mockUser);

        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");

        RegistrationResponseDTO savedUser = userService.registerUser(registrationRequestDTO);

        verify(userRepository, times(1)).save(any());

        assertEquals(mockUser.getUsername(), savedUser.getUsername());
        assertEquals(mockUser.getEmail(), savedUser.getEmail());
    }

    @Test
    void registerUser_UsernameAlreadyExists_ThrowsRegistrationTakenException() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("existingUser");
        registrationRequestDTO.setEmail("new@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the repository behavior to simulate an existing user
        when(userRepository.existsByUsername(any())).thenReturn(true);

        // Act and Assert
        RegistrationException exception = assertThrows(RegistrationException.class, () -> userService.registerUser(registrationRequestDTO));

        // Assert the exception message
        Assertions.assertEquals("Username is already taken", exception.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUser_EmailAlreadyExists_ThrowsRegistrationTakenException() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("newUser");
        registrationRequestDTO.setEmail("existing@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the repository behavior to simulate an existing email
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act and Assert
        RegistrationException exception = assertThrows(RegistrationException.class, () -> userService.registerUser(registrationRequestDTO));

        // Assert the exception message
        Assertions.assertEquals("Email is already taken", exception.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUser_UsernameAndEmailAlreadyExist_ThrowsRegistrationException() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUsername("existingUser");
        registrationRequestDTO.setEmail("existing@example.com");
        registrationRequestDTO.setPassword("testPassword");

        // Mock the repository behavior to simulate both existing username and email
        when(userRepository.existsByUsername(any())).thenReturn(true);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act and Assert
        RegistrationException exception = assertThrows(RegistrationException.class, () -> userService.registerUser(registrationRequestDTO));

        // Assert the exception message
        Assertions.assertEquals("Username is already taken", exception.getMessage());

        // Verify that the repository's save method was not called
        verify(userRepository, never()).save(any());
    }

    @Test
    void validateUserRegistration_UsernameTooShort_ThrowsConstraintViolationException() {
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
}