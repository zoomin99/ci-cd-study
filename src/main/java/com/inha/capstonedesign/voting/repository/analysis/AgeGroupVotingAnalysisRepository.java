package com.inha.capstonedesign.voting.repository.analysis;

import com.inha.capstonedesign.voting.entity.analysis.age.AgeGroupVotingAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeGroupVotingAnalysisRepository extends JpaRepository<AgeGroupVotingAnalysis, Long> {
}
