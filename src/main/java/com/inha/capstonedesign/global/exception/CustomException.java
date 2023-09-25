package com.inha.capstonedesign.global.exception;

public abstract class CustomException extends RuntimeException {

    public abstract CustomExceptionType getCustomExceptionType();

}
