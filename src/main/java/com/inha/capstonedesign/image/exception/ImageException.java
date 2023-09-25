package com.inha.capstonedesign.image.exception;

import com.inha.capstonedesign.global.exception.CustomException;
import com.inha.capstonedesign.global.exception.CustomExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageException extends CustomException {
    private final ImageExceptionType imageExceptionType;

    @Override
    public CustomExceptionType getCustomExceptionType() {
        return imageExceptionType;
    }
}
