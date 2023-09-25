package com.inha.capstonedesign.voting.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CandidateRequestDto {

    @NotNull
    private Long ballotId;
    @NotNull
    private String candidateName;
}
