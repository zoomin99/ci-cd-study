package com.inha.capstonedesign.voting.controller;

import com.inha.capstonedesign.global.response.PageResponseDto;
import com.inha.capstonedesign.member.dto.request.MemberRequestDto;
import com.inha.capstonedesign.voting.dto.request.VoteRequestDto;
import com.inha.capstonedesign.voting.dto.response.BallotResponseDto;
import com.inha.capstonedesign.voting.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

import static com.inha.capstonedesign.global.config.PageSizeConfig.BALLOT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voting")
public class VotingController {

    private final VotingService votingService;

    @GetMapping("/ballots")
    public ResponseEntity<PageResponseDto<BallotResponseDto.Page>> getBallotList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "진행중") String status) {
        if (page < 1) {
            page = 1;
        }

        PageRequest pageRequest = PageRequest.of(page - 1, BALLOT_PAGE_SIZE);
        PageResponseDto<BallotResponseDto.Page> ballotResponse = votingService.getBallotResponse(pageRequest, status);

        return ResponseEntity.ok(ballotResponse);
    }

    @GetMapping("/ballots/{ballotId}")
    public ResponseEntity<BallotResponseDto.Detail> getBallotDetail(@PathVariable Long ballotId,
                                                                    @AuthenticationPrincipal MemberRequestDto.Access access) {
        return ResponseEntity.ok(votingService.getBallotDetail(ballotId, access));
    }

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody @Valid VoteRequestDto voteDto,
                                       @AuthenticationPrincipal MemberRequestDto.Access access) {
        votingService.verifyAndUpdateVotingRecordStatus(voteDto, access);
        votingService.vote(voteDto, access);

        return ResponseEntity.ok(null);
    }

}
