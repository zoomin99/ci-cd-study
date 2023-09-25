package com.inha.capstonedesign.voting.solidity;

import com.inha.capstonedesign.global.web3j.GasProvider;
import com.inha.capstonedesign.global.web3j.Web3jProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolidityService {

    private final GasProvider gasProvider;
    private final Web3jProperties web3jProperties;
    private Credentials credentials;
    private Web3j web3j;
    private Voting votingContract;

    @PostConstruct
    public void initialize() {
        credentials = Credentials.create(web3jProperties.getWalletPrivateKey());
        web3j = Web3j.build(new HttpService(web3jProperties.getRpcEndpointUrl()));
        votingContract = Voting.load(web3jProperties.getContractAddress(), web3j, credentials, gasProvider);
    }

    public List<String> getBallotListFromEth() {
        try {
            List<Voting.Ballot> ballotList = votingContract.getBallotList().send();
            List<String> ballotNameList = ballotList.stream().map(Voting.Ballot::getBallotName)
                    .collect(Collectors.toList());
            return ballotNameList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getCandidateListFromEth(Integer ballotId) {
        try {
            List<Voting.Candidate> send = votingContract.getCandidateList(BigInteger.valueOf(ballotId)).send();
            List<String> candidateNameList = send.stream().map(Voting.Candidate::getName)
                    .collect(Collectors.toList());
            return candidateNameList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public BigInteger getVoteCount(Long ballotId, String candidateName) {
        try {
            BigInteger voteCount = votingContract.getVoteCount(BigInteger.valueOf(ballotId), candidateName).send();
            return voteCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigInteger.valueOf(-9999L);
    }
}
