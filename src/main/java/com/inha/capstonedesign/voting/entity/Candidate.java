package com.inha.capstonedesign.voting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inha.capstonedesign.image.entity.CandidateImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "candidate_name")
    @NotNull
    private String candidateName;

    @Column(name = "candidate_vote_count")
    @NotNull
    private Integer candidateVoteCount = 0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ballot_id")
    @Setter
    private Ballot ballot;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "candidate_image_id")
    @Setter
    private CandidateImage candidateImage;

    public void incrementCandidateVoteCount() {
        this.candidateVoteCount++;
    }

    public Candidate(String candidateName) {
        this.candidateName = candidateName;
    }
}
