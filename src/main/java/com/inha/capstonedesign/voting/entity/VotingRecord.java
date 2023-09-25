package com.inha.capstonedesign.voting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inha.capstonedesign.global.entity.BaseEntity;
import com.inha.capstonedesign.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "voting_record")
public class VotingRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voting_record_id")
    private Long voting_record_id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    private Member voter;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ballot_id")
    private Ballot ballot;

    @Column(name = "voting_record_status")
    @Enumerated(EnumType.STRING)
    VotingRecordStatus votingRecordStatus = VotingRecordStatus.IN_PROGRESS;

    public void changeVotingRecordStatus(VotingRecordStatus votingRecordStatus){
        this.votingRecordStatus = votingRecordStatus;
    }
    public VotingRecord(Member voter, Ballot ballot) {
        this.voter = voter;
        this.ballot = ballot;
    }
}
