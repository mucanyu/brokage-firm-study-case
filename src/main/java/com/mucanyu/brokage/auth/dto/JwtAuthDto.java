package com.mucanyu.brokage.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthDto {
    private final String tokenType = "Bearer";
    private final String accessToken;
}
