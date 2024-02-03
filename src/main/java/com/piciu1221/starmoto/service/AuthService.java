package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.LoginRequestDTO;
import com.piciu1221.starmoto.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public Authentication authenticateUser(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid username or password");
        } catch (LockedException e) {
            throw new IllegalArgumentException("Account locked. Please contact support.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Login failed: " + e.getMessage(), e);
        }
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}