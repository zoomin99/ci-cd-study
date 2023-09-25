package com.inha.capstonedesign.voting.repository;

import com.inha.capstonedesign.voting.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByCandidateId(Long candidateId);
}
