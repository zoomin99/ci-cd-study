package com.inha.capstonedesign.voting.entity.analysis.gender;

import com.inha.capstonedesign.member.entity.Gender;
import com.inha.capstonedesign.voting.entity.Candidate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gender_voting_analysis")
public class GenderVotingAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_voting_analysis_id")
    private Long genderVotingAnalysisId;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public GenderVotingAnalysis(Gender gender, Candidate candidate) {
        this.gender = gender;
        this.candidate = candidate;
    }
}
