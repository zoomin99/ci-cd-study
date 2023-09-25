package com.inha.capstonedesign.admin.controller;

import com.inha.capstonedesign.voting.dto.request.BallotRequestDto;
import com.inha.capstonedesign.voting.dto.request.CandidateRequestDto;
import com.inha.capstonedesign.voting.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/voting")
public class AdminVotingController {

    private final VotingService votingService;

    @PostMapping("/ballots")
    public ResponseEntity<String> addBallot(@RequestPart @Valid BallotRequestDto ballotRequestDto,
                                            @RequestPart MultipartFile ballotImage) throws IOException {
        votingService.addBallot(ballotRequestDto, ballotImage);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/candidates")
    public ResponseEntity<String> addCandidate(@RequestPart @Valid CandidateRequestDto candidateRequestDto,
                                               @RequestPart MultipartFile candidateImage) {
        votingService.addCandidate(candidateRequestDto, candidateImage);

        return ResponseEntity.ok(null);
    }
}
