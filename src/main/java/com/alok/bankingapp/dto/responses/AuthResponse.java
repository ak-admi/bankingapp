package com.alok.bankingapp.dto.responses;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
