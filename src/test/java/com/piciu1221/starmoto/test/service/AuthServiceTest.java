package com.piciu1221.starmoto.test.service;

import com.piciu1221.starmoto.dto.LoginRequestDTO;
import com.piciu1221.starmoto.security.JwtUtil;
import com.piciu1221.starmoto.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void authenticateUser_ValidCredentials_ReturnsAuthentication() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUser");
        loginRequestDTO.setPassword("testPassword");

        // Mock the authentication manager behavior
        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);

        Authentication result = authService.authenticateUser(loginRequestDTO);

        // Act and Assert
        assertNotNull(result);
        assertEquals(mockAuthentication, result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticateUser_InvalidCredentials_ThrowsIllegalArgumentException() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("invalidUser");
        loginRequestDTO.setPassword("invalidPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> authService.authenticateUser(loginRequestDTO));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticateUser_LockedAccount_ThrowsIllegalArgumentException() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("lockedUser");
        loginRequestDTO.setPassword("lockedPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("Account locked"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> authService.authenticateUser(loginRequestDTO));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void generateToken_ValidAuthentication_ReturnsToken() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        UserDetails userDetails = new User("testUser", "testPassword", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(mockAuthentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken("testUser")).thenReturn("mockToken");

        // Act
        String result = authService.generateToken(mockAuthentication);

        // Assert
        assertEquals("mockToken", result);
        verify(jwtUtil, times(1)).generateToken("testUser");
    }
}
