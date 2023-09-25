package com.inha.capstonedesign.voting.solidity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/voting/solidity")
public class SolidityController {

    private final SolidityService solidityService;

    @GetMapping("/ballots")
    public ResponseEntity<List<String>> getBallotList() {
        List<String> ballotList = solidityService.getBallotListFromEth();

        return ResponseEntity.ok(ballotList);
    }

    @GetMapping("/candidates/{ballotId}")
    public ResponseEntity<List<String>> getCandidateList(@PathVariable Integer ballotId) {
        return ResponseEntity.ok(solidityService.getCandidateListFromEth(ballotId));
    }

    @GetMapping("/vote-count/{ballotId}/{candidateName}")
    public ResponseEntity<BigInteger> getVoteCount(@PathVariable Long ballotId, @PathVariable String candidateName) {
        return ResponseEntity.ok(solidityService.getVoteCount(ballotId, candidateName));
    }
}
