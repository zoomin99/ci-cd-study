package com.inha.capstonedesign.voting.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class VoteRequestDto {
    @NotNull
    private Long ballotId;
    @NotNull
    private Long candidateId;
}
