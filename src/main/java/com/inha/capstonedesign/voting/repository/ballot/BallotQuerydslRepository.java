package com.inha.capstonedesign.voting.repository.ballot;

import com.inha.capstonedesign.voting.entity.Ballot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BallotQuerydslRepository {

    Optional<Ballot> findByBallotIdWithImage(Long ballotId);

    Page<Ballot> findAllByBallotStatusOrderByBallotEndDateTime(Pageable pageable, String status);

    List<Ballot> findNotStartedBallotsAfterStartTime();

    List<Ballot> findInProgressBallotsAfterEndTime();
}
