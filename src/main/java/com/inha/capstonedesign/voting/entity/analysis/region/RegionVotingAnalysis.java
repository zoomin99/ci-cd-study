package com.inha.capstonedesign.voting.entity.analysis.region;

import com.inha.capstonedesign.member.entity.Region;
import com.inha.capstonedesign.voting.entity.Candidate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "region_voting_analysis")
public class RegionVotingAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_voting_analysis_id")
    private Long regionVotingAnalysisId;

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public RegionVotingAnalysis(Region region, Candidate candidate) {
        this.region = region;
        this.candidate = candidate;
    }
}
