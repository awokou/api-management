package com.server.api.management.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.server.api.management.dto.AuthResponse;
import com.server.api.management.dto.LoginDto;
import com.server.api.management.entity.User;
import com.server.api.management.entity.enums.TokenType;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.repository.UserRepository;
import com.server.api.management.security.JwtUtils;
import com.server.api.management.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    public AuthResponse authenticate(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            //
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateJwtToken(authentication);
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return AuthResponse.builder()
                    .accessToken(token)
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .roles(user.getRoles().name())
                    .tokenType(TokenType.BEARER.name())
                    .build();

        } catch (BadCredentialsException ex) {
            throw new ResourceNotFoundException("Invalid Username or Password");
        }
    }
}
