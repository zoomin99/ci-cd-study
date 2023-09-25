package com.inha.capstonedesign.image.exception;

import com.inha.capstonedesign.global.exception.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ImageExceptionType implements CustomExceptionType {

    EXCEED_LIMIT_SIZE(HttpStatus.BAD_REQUEST, "I001", "파일 크기가 제한 크기인 10MB를 넘겼습니다"),
    EMPTY_IMAGE(HttpStatus.BAD_REQUEST, "I002", "없는 파일이나 빈 파일을 업로드했습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
