package com.inha.capstonedesign.auth.exception;

import com.inha.capstonedesign.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthException extends CustomException {

    private final AuthExceptionType authExceptionType;

    @Override
    public AuthExceptionType getCustomExceptionType() {
        return authExceptionType;
    }
}