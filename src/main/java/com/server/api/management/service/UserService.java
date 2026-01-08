package com.server.api.management.service;

import com.server.api.management.dto.AuthResponse;
import com.server.api.management.dto.LoginDto;

public interface UserService {
    AuthResponse authenticate(LoginDto loginDto);
}
