package com.server.api.management.service;

import com.server.api.management.domain.dto.response.AuthResponse;
import com.server.api.management.domain.dto.external.LoginDto;

public interface UserService {
    AuthResponse authenticate(LoginDto loginDto);
}
