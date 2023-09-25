package com.inha.capstonedesign.global.exception;

import org.springframework.http.HttpStatus;

public interface CustomExceptionType {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}
