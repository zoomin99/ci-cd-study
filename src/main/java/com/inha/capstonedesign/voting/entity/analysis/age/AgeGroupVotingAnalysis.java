package com.inha.capstonedesign.voting.entity.analysis.age;

import com.inha.capstonedesign.voting.entity.Candidate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "age_group_voting_analysis")
public class AgeGroupVotingAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_group_voting_analysis_id")
    private Long ageGroupVotingAnalysisId;

    @Column(name = "ageGroup")
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public AgeGroupVotingAnalysis(AgeGroup ageGroup, Candidate candidate) {
        this.ageGroup = ageGroup;
        this.candidate = candidate;
    }
}
