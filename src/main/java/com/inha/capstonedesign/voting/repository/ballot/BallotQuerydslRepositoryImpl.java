package com.inha.capstonedesign.voting.repository.ballot;

import com.inha.capstonedesign.voting.entity.Ballot;
import com.inha.capstonedesign.voting.entity.BallotStatus;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.inha.capstonedesign.image.entity.QBallotImage.ballotImage;
import static com.inha.capstonedesign.voting.entity.QBallot.ballot;

@RequiredArgsConstructor
@Repository
public class BallotQuerydslRepositoryImpl implements BallotQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Ballot> findByBallotIdWithImage(Long ballotId) {
        return queryFactory.selectFrom(ballot)
                .leftJoin(ballot.ballotImage, ballotImage)
                .fetchJoin()
                .where(ballot.ballotId.eq(ballotId))
                .fetch()
                .stream().findAny();
    }

    @Override
    public Page<Ballot> findAllByBallotStatusOrderByBallotEndDateTime(Pageable pageable, String status) {

        BallotStatus ballotStatus = getBallotStatusFromString(status);

        List<Ballot> content = queryFactory.selectFrom(ballot)
                .leftJoin(ballot.ballotImage, ballotImage)
                .fetchJoin()
                .where(ballot.ballotStatus.eq(ballotStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ballot.ballotEndDateTime.asc())
                .fetch();

        Long total = queryFactory.select(Wildcard.count)
                .from(ballot)
                .where(ballot.ballotStatus.eq(ballotStatus))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<Ballot> findNotStartedBallotsAfterStartTime() {

        LocalDateTime now = LocalDateTime.now();

        return queryFactory.selectFrom(ballot)
                .where(ballot.ballotStartDateTime.before(now), ballot.ballotStatus.eq(BallotStatus.NOT_STARTED))
                .fetch();
    }

    @Override
    public List<Ballot> findInProgressBallotsAfterEndTime() {
        LocalDateTime now = LocalDateTime.now();

        return queryFactory.selectFrom(ballot)
                .where(ballot.ballotEndDateTime.before(now), ballot.ballotStatus.eq(BallotStatus.IN_PROGRESS))
                .fetch();
    }

    private static BallotStatus getBallotStatusFromString(String status) {
        if (status == null) {
            return BallotStatus.IN_PROGRESS;
        }
        for (BallotStatus ballotStatus : BallotStatus.values()) {
            if (ballotStatus.getKorean().equals(status)) {
                return ballotStatus;
            }
        }
        return BallotStatus.IN_PROGRESS;
    }
}
