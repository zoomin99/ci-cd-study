package com.inha.capstonedesign.voting.entity;

import com.inha.capstonedesign.image.entity.BallotImage;
import com.inha.capstonedesign.member.entity.Gender;
import com.inha.capstonedesign.member.entity.Region;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ballot")
public class Ballot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ballot_id")
    private Long ballotId;

    @Column(name = "ballot_name")
    @NotNull
    private String ballotName;

    @Column(name = "ballot_start_date_time")
    private LocalDateTime ballotStartDateTime;

    @Column(name = "ballot_end_date_time")
    private LocalDateTime ballotEndDateTime;

    @Column(name = "ballot_min_age")
    private Integer ballotMinAge;

    @Column(name = "ballot_max_age")
    private Integer ballotMaxAge;

    @Column(name = "ballot_subject_region")
    @Enumerated(EnumType.STRING)
    private Region ballotSubjectRegion;

    @Column(name = "ballot_subject_gender")
    @Enumerated(EnumType.STRING)
    private Gender ballotSubjectGender;

    @Column(name = "ballot_brief_description")
    @NotNull
    private String ballotBriefDescription;

    @Column(name = "ballot_detail_description")
    @NotNull
    private String ballotDetailDescription;

    @Column(name = "ballot_status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BallotStatus ballotStatus;

    @OneToMany(mappedBy = "ballot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates = new ArrayList<>();

    @OneToMany(mappedBy = "ballot")
    private List<VotingRecord> votingRecords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ballot_image_id")
    @Setter
    private BallotImage ballotImage;

    public void changeBallotStatus(BallotStatus ballotStatus) {
        this.ballotStatus = ballotStatus;
    }

    public void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setBallot(this);
    }

    @Builder
    public Ballot(String ballotName, LocalDateTime ballotStartDateTime, LocalDateTime ballotEndDateTime, Integer ballotMinAge, Integer ballotMaxAge, Region ballotSubjectRegion, Gender ballotSubjectGender, String ballotBriefDescription, String ballotDetailDescription) {
        this.ballotName = ballotName;
        this.ballotStartDateTime = ballotStartDateTime;
        this.ballotEndDateTime = ballotEndDateTime;
        this.ballotStatus = BallotStatus.NOT_STARTED;
        this.ballotMinAge = ballotMinAge;
        this.ballotMaxAge = ballotMaxAge;
        this.ballotSubjectRegion = ballotSubjectRegion;
        this.ballotSubjectGender = ballotSubjectGender;
        this.ballotBriefDescription = ballotBriefDescription;
        this.ballotDetailDescription = ballotDetailDescription;
    }
}
