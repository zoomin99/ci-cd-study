package com.inha.capstonedesign.voting.exception;

import com.inha.capstonedesign.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VotingException extends CustomException {

    private final VotingExceptionType authExceptionType;

    @Override
    public VotingExceptionType getCustomExceptionType() {
        return authExceptionType;
    }
}