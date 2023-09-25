package com.inha.capstonedesign.voting.service;

import com.inha.capstonedesign.auth.exception.AuthException;
import com.inha.capstonedesign.auth.exception.AuthExceptionType;
import com.inha.capstonedesign.global.response.PageResponseDto;
import com.inha.capstonedesign.global.web3j.GasProvider;
import com.inha.capstonedesign.global.web3j.Web3jProperties;
import com.inha.capstonedesign.image.ImageUploadService;
import com.inha.capstonedesign.image.entity.Image;
import com.inha.capstonedesign.member.dto.request.MemberRequestDto;
import com.inha.capstonedesign.member.entity.Member;
import com.inha.capstonedesign.member.entity.Role;
import com.inha.capstonedesign.member.repository.MemberRepository;
import com.inha.capstonedesign.voting.dto.request.BallotRequestDto;
import com.inha.capstonedesign.voting.dto.request.CandidateRequestDto;
import com.inha.capstonedesign.voting.dto.request.VoteRequestDto;
import com.inha.capstonedesign.voting.dto.response.BallotResponseDto;
import com.inha.capstonedesign.voting.entity.*;
import com.inha.capstonedesign.voting.entity.analysis.age.AgeGroupVotingAnalysis;
import com.inha.capstonedesign.voting.entity.analysis.gender.GenderVotingAnalysis;
import com.inha.capstonedesign.voting.entity.analysis.region.RegionVotingAnalysis;
import com.inha.capstonedesign.voting.exception.VotingException;
import com.inha.capstonedesign.voting.exception.VotingExceptionType;
import com.inha.capstonedesign.voting.repository.CandidateRepository;
import com.inha.capstonedesign.voting.repository.VotingRecordRepository;
import com.inha.capstonedesign.voting.repository.analysis.AgeGroupVotingAnalysisRepository;
import com.inha.capstonedesign.voting.repository.analysis.GenderVotingAnalysisRepository;
import com.inha.capstonedesign.voting.repository.analysis.RegionVotingAnalysisRepository;
import com.inha.capstonedesign.voting.repository.ballot.BallotRepository;
import com.inha.capstonedesign.voting.solidity.Voting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VotingService {

    private final GasProvider gasProvider;
    private final Web3jProperties web3jProperties;
    private Credentials credentials;
    private Web3j web3j;
    private Voting votingContract;

    private final MemberRepository memberRepository;
    private final BallotRepository ballotRepository;
    private final CandidateRepository candidateRepository;
    private final VotingRecordRepository votingRecordRepository;

    private final AgeGroupVotingAnalysisRepository ageGroupRepository;
    private final GenderVotingAnalysisRepository genderRepository;
    private final RegionVotingAnalysisRepository regionRepository;

    private final ImageUploadService imageUploadService;

    @PostConstruct
    public void initialize() {
        credentials = Credentials.create(web3jProperties.getWalletPrivateKey());
        web3j = Web3j.build(new HttpService(web3jProperties.getRpcEndpointUrl()));
        votingContract = Voting.load(web3jProperties.getContractAddress(), web3j, credentials, gasProvider);
    }

    public PageResponseDto<BallotResponseDto.Page> getBallotResponse(Pageable pageable, String status) {
        Page<Ballot> ballots = ballotRepository.findAllByBallotStatusOrderByBallotEndDateTime(pageable, status);
        List<BallotResponseDto.Page> ballotResponseDtos = ballots.getContent().stream().map(BallotResponseDto.Page::of).collect(Collectors.toList());
        return new PageResponseDto<>(new PageImpl<>(ballotResponseDtos, pageable, ballots.getTotalElements()));
    }

    public BallotResponseDto.Detail getBallotDetail(Long ballotId, MemberRequestDto.Access access) {
        Member member = memberRepository.findByMemberEmail(access.getEmail())
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        Ballot ballot = ballotRepository.findByBallotIdWithImage(ballotId)
                .orElseThrow(() -> new VotingException(VotingExceptionType.BALLOT_NOT_EXISTS));

        Optional<VotingRecord> votingRecord = votingRecordRepository.findByVoterAndBallot(member, ballot);

        boolean isSubject = verifySubject(member, ballot);
        boolean notVoted = checkNotVoted(votingRecord);
        return BallotResponseDto.Detail.of(ballot, isSubject, notVoted);
    }

    @Transactional
    public void addBallot(BallotRequestDto ballotRequestDto, MultipartFile ballotImage) throws IOException {
        try {
            if (ballotRequestDto.getBallotEndDateTime().isBefore(ballotRequestDto.getBallotStartDateTime())) {
                throw new VotingException(VotingExceptionType.BALLOT_END_TIME_BEFORE_START_TIME);
            }
            votingContract.addBallot(ballotRequestDto.getBallotName()).send();
            Ballot ballot = ballotRequestDto.toEntity();

            Image image = imageUploadService.uploadImage(ballotImage);
            ballot.setBallotImage(image.toBallotImage());
            ballotRepository.save(ballot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void addCandidate(CandidateRequestDto candidateDto, MultipartFile candidateImage) {
        try {
            votingContract.addCandidate(BigInteger.valueOf(candidateDto.getBallotId()), candidateDto.getCandidateName()).send();

            Ballot ballot = ballotRepository.findByBallotId(candidateDto.getBallotId())
                    .orElseThrow(() -> new VotingException(VotingExceptionType.BALLOT_NOT_EXISTS));


            Candidate candidate = new Candidate(candidateDto.getCandidateName());
            Image image = imageUploadService.uploadImage(candidateImage);
            candidate.setCandidateImage(image.toCandidateImage());

            ballot.addCandidate(candidate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void verifyAndUpdateVotingRecordStatus(VoteRequestDto voteDto, MemberRequestDto.Access access) {
        Member member = memberRepository.findByMemberEmail(access.getEmail())
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        Ballot ballot = ballotRepository.findByBallotId(voteDto.getBallotId())
                .orElseThrow(() -> new VotingException(VotingExceptionType.BALLOT_NOT_EXISTS));

        if (!verifySubject(member, ballot)) {
            throw new VotingException(VotingExceptionType.NOT_SUBJECT);
        }

        Optional<VotingRecord> votingRecord = votingRecordRepository.findByVoterAndBallot(member, ballot);

        if (votingRecord.isPresent()) {
            if (votingRecord.get().getVotingRecordStatus().equals(VotingRecordStatus.IN_PROGRESS)) {
                throw new VotingException(VotingExceptionType.VOTING_IN_PROGRESS);
            } else if (votingRecord.get().getVotingRecordStatus().equals(VotingRecordStatus.COMPLETED)) {
                throw new VotingException(VotingExceptionType.ALREADY_VOTED);
            } else if (votingRecord.get().getVotingRecordStatus().equals(VotingRecordStatus.CANCELLED_ERROR)) {
                votingRecordRepository.delete(votingRecord.get());
            }
        }
        votingRecordRepository.save(new VotingRecord(member, ballot));
    }

    @Transactional
    public void vote(VoteRequestDto voteDto, MemberRequestDto.Access access) {
        Member member = memberRepository.findByMemberEmail(access.getEmail())
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        Ballot ballot = ballotRepository.findByBallotId(voteDto.getBallotId())
                .orElseThrow(() -> new VotingException(VotingExceptionType.BALLOT_NOT_EXISTS));

        VotingRecord votingRecord = votingRecordRepository.findByVoterAndBallot(member, ballot)
                .orElseThrow(() -> new VotingException(VotingExceptionType.UNKNOWN_ERROR));

        try {
            Candidate candidate = candidateRepository.findByCandidateId(voteDto.getCandidateId())
                    .orElseThrow(() -> new VotingException(VotingExceptionType.CANDIDATE_NOT_EXISTS));

            votingContract.voteForCandidate(BigInteger.valueOf(voteDto.getBallotId()), candidate.getCandidateName()).send();
            candidate.incrementCandidateVoteCount();
            votingRecord.changeVotingRecordStatus(VotingRecordStatus.COMPLETED);

            ageGroupRepository.save(new AgeGroupVotingAnalysis(member.getAgeGroup(), candidate));
            genderRepository.save(new GenderVotingAnalysis(member.getMemberGender(), candidate));
            regionRepository.save(new RegionVotingAnalysis(member.getMemberRegion(), candidate));

        } catch (Exception e) {
            votingRecord.changeVotingRecordStatus(VotingRecordStatus.CANCELLED_ERROR);
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    //정각 매 1시간마다로 설정
    //매 10초마다 같은 경우엔 0/10 * * 이런식으로
    @Transactional
    public void votingSchedule() {
        List<Ballot> notStartedBallots = ballotRepository.findNotStartedBallotsAfterStartTime();
        List<Ballot> inProgressBallots = ballotRepository.findInProgressBallotsAfterEndTime();

        notStartedBallots.stream()
                .forEach(ballot -> ballot.changeBallotStatus(BallotStatus.IN_PROGRESS));
        inProgressBallots.stream()
                .forEach(ballot -> ballot.changeBallotStatus(BallotStatus.CLOSED));
    }

    private boolean verifySubject(Member member, Ballot ballot) {

        if (!member.getRoles().contains(Role.ROLE_USER)) {
            return false;
        }
        if (ballot.getBallotMinAge() != null) {
            if (member.getMemberAge() < ballot.getBallotMinAge()) {
                return false;
            }
        }
        if (ballot.getBallotMaxAge() != null) {
            if (member.getMemberAge() > ballot.getBallotMaxAge()) {
                return false;
            }
        }
        if (ballot.getBallotSubjectRegion() != null) {
            if (!member.getMemberRegion().equals(ballot.getBallotSubjectRegion())) {
                return false;
            }
        }
        if (ballot.getBallotSubjectGender() != null) {
            if (!member.getMemberGender().equals(ballot.getBallotSubjectGender())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkNotVoted(Optional<VotingRecord> votingRecord) {
        if (votingRecord.isPresent()) {
            if (votingRecord.get().getVotingRecordStatus().equals(VotingRecordStatus.CANCELLED_ERROR)) {
                return true;
            }
            return false;
        }
        return true;
    }
}
