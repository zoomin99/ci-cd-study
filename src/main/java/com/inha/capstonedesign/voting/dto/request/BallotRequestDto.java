package com.inha.capstonedesign.voting.dto.request;

import com.inha.capstonedesign.member.entity.Gender;
import com.inha.capstonedesign.member.entity.Region;
import com.inha.capstonedesign.voting.entity.Ballot;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class BallotRequestDto {
    @NotNull
    private String ballotName;

    @NotNull
    private LocalDateTime ballotStartDateTime;

    @NotNull
    private LocalDateTime ballotEndDateTime;

    private Integer ballotMinAge;

    private Integer ballotMaxAge;

    private String ballotSubjectRegion;

    private String ballotSubjectGender;

    @NotNull
    private String ballotBriefDescription;

    @NotNull
    private String ballotDetailDescription;

    private Region setEnumSubjectRegion(String region) {
        if (region.equals(Region.GYEONGGI.getKorean())) {
            return Region.GYEONGGI;
        } else if (region.equals(Region.GANGWON.getKorean())) {
            return Region.GANGWON;
        } else if (region.equals(Region.CHUNGCHEONGBUK.getKorean())) {
            return Region.CHUNGCHEONGBUK;
        } else if (region.equals(Region.CHUNGCHEONGNAM.getKorean())) {
            return Region.CHUNGCHEONGNAM;
        } else if (region.equals(Region.JEOLLABUK.getKorean())) {
            return Region.JEOLLABUK;
        } else if (region.equals(Region.JEOLLANAM.getKorean())) {
            return Region.JEOLLANAM;
        } else if (region.equals(Region.GYEONGSANGBUK.getKorean())) {
            return Region.GYEONGSANGBUK;
        } else if (region.equals(Region.GYEONGSANGNAM.getKorean())) {
            return Region.GYEONGSANGNAM;
        } else {
            return Region.SEOUL;
        }
    }

    private Gender setEnumSubjectGender(String gender) {
        if (gender.equals(Gender.MALE.getKorean())) {
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }
    }

    public Ballot toEntity() {
        Ballot.BallotBuilder ballotBuilder = Ballot.builder()
                .ballotName(ballotName)
                .ballotStartDateTime(ballotStartDateTime)
                .ballotEndDateTime(ballotEndDateTime)
                .ballotMinAge(ballotMinAge)
                .ballotMaxAge(ballotMaxAge)
                .ballotBriefDescription(ballotBriefDescription)
                .ballotDetailDescription(ballotDetailDescription);

        if (ballotSubjectRegion != null) {
            ballotBuilder.ballotSubjectRegion(setEnumSubjectRegion(ballotSubjectRegion));
        }

        if (ballotSubjectGender != null) {
            ballotBuilder.ballotSubjectGender(setEnumSubjectGender(ballotSubjectGender));
        }
        return ballotBuilder.build();
    }
}
