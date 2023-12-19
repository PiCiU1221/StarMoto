package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.ApiResponse;
import com.piciu1221.starmoto.dto.RegistrationRequest;
import com.piciu1221.starmoto.exception.EmailTakenException;
import com.piciu1221.starmoto.exception.UsernameTakenException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public ApiResponse<String> registerUser(RegistrationRequest registrationRequest) {
        try {
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(registrationRequest.getPassword());

            validateUserRegistration(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return ApiResponse.customSuccess(HttpStatus.CREATED, "User registered successfully");
        } catch (UsernameTakenException | EmailTakenException e) {
            return ApiResponse.error(HttpStatus.CONFLICT, e.getMessage());
        } catch (ConstraintViolationException e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void validateUserRegistration(User user) {
        // List because I want to return only the first violation that occur
        // With set if multiple violations were present it gave random answers because of this data structure
        // Random output is unable to be tested consistently
        List<ConstraintViolation<User>> violationsList = new ArrayList<>(validator.validate(user));

        if (!violationsList.isEmpty()) {
            // Sort the list alphabetically based on the error messages
            violationsList.sort(Comparator.comparing(ConstraintViolation::getMessage));

            ConstraintViolation<User> firstViolation = violationsList.get(0);
            String firstErrorMessage = firstViolation.getMessage();

            // Create a set with the same data for the exception return type
            Set<ConstraintViolation<?>> violationsSet = new HashSet<>(violationsList);

            throw new ConstraintViolationException(firstErrorMessage, violationsSet);
        }

        if (existsByUsername(user.getUsername())) {
            throw new UsernameTakenException("Username is already taken");
        }

        if (existsByEmail(user.getEmail())) {
            throw new EmailTakenException("Email is already taken");
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")  // You might want to load roles from your database
                .build();
    }
}