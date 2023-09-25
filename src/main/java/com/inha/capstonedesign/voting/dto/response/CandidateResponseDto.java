package com.inha.capstonedesign.voting.dto.response;

import com.inha.capstonedesign.voting.entity.Candidate;
import lombok.Builder;
import lombok.Getter;

public class CandidateResponseDto {

    @Getter
    public static class Detail {

        private Long candidateId;
        private String candidateName;
        private Integer candidateVoteCount;
        private String candidateImage;

        @Builder
        public Detail(Long candidateId, String candidateName, Integer candidateVoteCount, String candidateImage) {
            this.candidateId = candidateId;
            this.candidateName = candidateName;
            this.candidateVoteCount = candidateVoteCount;
            this.candidateImage = candidateImage;
        }

        public static Detail of(Candidate candidate) {
            DetailBuilder detailBuilder = Detail.builder()
                    .candidateId(candidate.getCandidateId())
                    .candidateName(candidate.getCandidateName())
                    .candidateVoteCount(candidate.getCandidateVoteCount());

            if (candidate.getCandidateImage() != null) {
                detailBuilder.candidateImage(candidate.getCandidateImage().getImagePath());
            }
            return detailBuilder.build();
        }
    }
}
