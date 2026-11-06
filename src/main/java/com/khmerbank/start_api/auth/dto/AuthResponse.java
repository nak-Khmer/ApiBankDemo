package com.khmerbank.start_api.auth.dto;

public record AuthResponse(
    String token,
    String username,
    String role
) {}