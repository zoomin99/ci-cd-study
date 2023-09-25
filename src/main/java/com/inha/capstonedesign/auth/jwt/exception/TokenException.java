package com.inha.capstonedesign.auth.jwt.exception;

import com.inha.capstonedesign.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenException extends CustomException {

    private final TokenExceptionType tokenExceptionType;

    @Override
    public TokenExceptionType getCustomExceptionType() {
        return tokenExceptionType;
    }
}
