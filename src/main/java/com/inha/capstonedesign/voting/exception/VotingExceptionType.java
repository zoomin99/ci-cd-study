package com.inha.capstonedesign.voting.exception;

import com.inha.capstonedesign.global.exception.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum VotingExceptionType implements CustomExceptionType {

    BALLOT_END_TIME_BEFORE_START_TIME(HttpStatus.BAD_REQUEST, "V001", "투표 종료일이 시작일 이전입니다"),
    BALLOT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "V002", "존재하지 않는 투표입니다"),

    CANDIDATE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "V003", "존재하지 않는 후보자입니다"),
    NOT_SUBJECT(HttpStatus.BAD_REQUEST, "V004", "투표 대상이 아닙니다"),
    VOTING_IN_PROGRESS(HttpStatus.BAD_REQUEST, "V005", "투표를 반영중입니다"),
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "V006", "이미 투표를 행사한 투표입니다"),
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "V007", "투표중 알 수 없는 에러가 발생했습니다");

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