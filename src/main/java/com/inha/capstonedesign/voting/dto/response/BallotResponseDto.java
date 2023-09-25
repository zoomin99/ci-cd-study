package com.inha.capstonedesign.voting.dto.response;

import com.inha.capstonedesign.voting.entity.Ballot;
import com.inha.capstonedesign.voting.entity.Candidate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BallotResponseDto {

    @Getter
    public static class Page {
        private String ballotName;
        private LocalDateTime ballotStartDateTime;
        private LocalDateTime ballotEndDateTime;
        private Integer ballotMinAge;
        private Integer ballotMaxAge;
        private String ballotSubjectRegion;
        private String ballotSubjectGender;
        private String ballotBriefDescription;
        private String ballotImage;
        private String ballotStatus;

        @Builder
        public Page(String ballotName, LocalDateTime ballotStartDateTime, LocalDateTime ballotEndDateTime, Integer ballotMinAge, Integer ballotMaxAge, String ballotSubjectRegion, String ballotSubjectGender, String ballotBriefDescription, String ballotImage, String ballotStatus) {
            this.ballotName = ballotName;
            this.ballotStartDateTime = ballotStartDateTime;
            this.ballotEndDateTime = ballotEndDateTime;
            this.ballotMinAge = ballotMinAge;
            this.ballotMaxAge = ballotMaxAge;
            this.ballotSubjectRegion = ballotSubjectRegion;
            this.ballotSubjectGender = ballotSubjectGender;
            this.ballotBriefDescription = ballotBriefDescription;
            this.ballotImage = ballotImage;
            this.ballotStatus = ballotStatus;
        }

        public static Page of(Ballot ballot) {
            PageBuilder ballotResponseDtoBuilder = Page.builder()
                    .ballotName(ballot.getBallotName())
                    .ballotStartDateTime(ballot.getBallotStartDateTime())
                    .ballotEndDateTime(ballot.getBallotEndDateTime())
                    .ballotMinAge(ballot.getBallotMinAge())
                    .ballotMaxAge(ballot.getBallotMaxAge())
                    .ballotBriefDescription(ballot.getBallotBriefDescription())
                    .ballotStatus(ballot.getBallotStatus().getKorean());

            if (ballot.getBallotSubjectRegion() != null) {
                ballotResponseDtoBuilder.ballotSubjectRegion(ballot.getBallotSubjectRegion().getKorean());
            }
            if (ballot.getBallotSubjectGender() != null) {
                ballotResponseDtoBuilder.ballotSubjectGender(ballot.getBallotSubjectGender().getKorean());
            }
            if (ballot.getBallotImage() != null) {
                ballotResponseDtoBuilder.ballotImage(ballot.getBallotImage().getImagePath());
            }
            return ballotResponseDtoBuilder.build();
        }
    }

    @Getter
    public static class Detail {
        private String ballotName;
        private LocalDateTime ballotStartDateTime;
        private LocalDateTime ballotEndDateTime;
        private Integer ballotMinAge;
        private Integer ballotMaxAge;
        private String ballotSubjectRegion;
        private String ballotSubjectGender;
        private String ballotBriefDescription;
        private String ballotDetailDescription;
        private List<CandidateResponseDto.Detail> candidates = new ArrayList<>();
        private String ballotImage;
        private String ballotStatus;
        private Boolean isSubject;
        private Boolean notVoted;

        @Builder
        public Detail(String ballotName, LocalDateTime ballotStartDateTime, LocalDateTime ballotEndDateTime, Integer ballotMinAge, Integer ballotMaxAge, String ballotSubjectRegion, String ballotSubjectGender, String ballotBriefDescription, String ballotDetailDescription, List<Candidate> candidates, String ballotImage, String ballotStatus, Boolean isSubject, Boolean notVoted) {
            this.ballotName = ballotName;
            this.ballotStartDateTime = ballotStartDateTime;
            this.ballotEndDateTime = ballotEndDateTime;
            this.ballotMinAge = ballotMinAge;
            this.ballotMaxAge = ballotMaxAge;
            this.ballotSubjectRegion = ballotSubjectRegion;
            this.ballotSubjectGender = ballotSubjectGender;
            this.ballotBriefDescription = ballotBriefDescription;
            this.ballotDetailDescription = ballotDetailDescription;
            this.candidates.addAll(
                    candidates.stream().map(CandidateResponseDto.Detail::of).collect(Collectors.toList()));
            this.ballotImage = ballotImage;
            this.ballotStatus = ballotStatus;
            this.isSubject = isSubject;
            this.notVoted = notVoted;
        }

        public static Detail of(Ballot ballot, Boolean isSubject, Boolean notVoted) {

            Detail.DetailBuilder detailBuilder = Detail.builder()
                    .ballotName(ballot.getBallotName())
                    .ballotStartDateTime(ballot.getBallotStartDateTime())
                    .ballotEndDateTime(ballot.getBallotEndDateTime())
                    .ballotMinAge(ballot.getBallotMinAge())
                    .ballotMaxAge(ballot.getBallotMaxAge())
                    .ballotBriefDescription(ballot.getBallotBriefDescription())
                    .ballotDetailDescription(ballot.getBallotDetailDescription())
                    .candidates(ballot.getCandidates())
                    .ballotStatus(ballot.getBallotStatus().getKorean())
                    .isSubject(isSubject)
                    .notVoted(notVoted);

            if (ballot.getBallotSubjectRegion() != null) {
                detailBuilder.ballotSubjectRegion(ballot.getBallotSubjectRegion().getKorean());
            }
            if (ballot.getBallotSubjectGender() != null) {
                detailBuilder.ballotSubjectGender(ballot.getBallotSubjectGender().getKorean());
            }
            if (ballot.getBallotImage() != null) {
                detailBuilder.ballotImage(ballot.getBallotImage().getImagePath());
            }
            return detailBuilder.build();
        }
    }
}
