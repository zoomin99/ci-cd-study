package com.inha.capstonedesign.voting.repository.analysis;

import com.inha.capstonedesign.voting.entity.analysis.region.RegionVotingAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionVotingAnalysisRepository extends JpaRepository<RegionVotingAnalysis, Long> {
}
