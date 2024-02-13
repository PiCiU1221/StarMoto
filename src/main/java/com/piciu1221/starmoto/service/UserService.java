package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import com.piciu1221.starmoto.dto.RegistrationResponseDTO;
import com.piciu1221.starmoto.exception.RegistrationException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public RegistrationResponseDTO registerUser(RegistrationRequestDTO registrationRequestDTO) throws RegistrationException, ConstraintViolationException {
        User user = new User();
        user.setEmail(registrationRequestDTO.getEmail());
        user.setUsername(registrationRequestDTO.getUsername());
        user.setPassword(registrationRequestDTO.getPassword());

        validateUserRegistration(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}