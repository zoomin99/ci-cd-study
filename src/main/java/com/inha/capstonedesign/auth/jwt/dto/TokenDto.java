package com.inha.capstonedesign.auth.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private String accessToken;
    private String refreshToken;
}
