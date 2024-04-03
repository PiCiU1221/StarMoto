package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.LoginRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationResponseDTO;
import com.piciu1221.starmoto.exception.AuthenticationFailedException;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.security.JwtService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

            User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                    .orElseThrow(() -> new AuthenticationFailedException("User not found"));

            return jwtService.generateToken(user);
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid username or password");
        } catch (LockedException e) {
            throw new AuthenticationFailedException("Account locked. Please contact support.");
        } catch (Exception e) {
            throw new AuthenticationFailedException("Login failed: " + e.getMessage(), e);
        }
    }

    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) throws RegistrationException, ConstraintViolationException {
        User user = new User();
        user.setEmail(registrationRequestDTO.getEmail());
        user.setUsername(registrationRequestDTO.getUsername());
        user.setPassword(registrationRequestDTO.getPassword());

        validateUserRegistration(user);
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));

        return new RegistrationResponseDTO(userRepository.save(user));
    }

    public void validateUserRegistration(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (existsByUsername) {
            throw new RegistrationException("Username is already taken");
        }

        if (existsByEmail) {
            throw new RegistrationException("Email is already taken");
        }
    }
}