package com.inha.capstonedesign.voting.repository.ballot;

import com.inha.capstonedesign.voting.entity.Ballot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BallotRepository extends JpaRepository<Ballot, Long>, BallotQuerydslRepository {

    Optional<Ballot> findByBallotId(Long ballotId);
}
