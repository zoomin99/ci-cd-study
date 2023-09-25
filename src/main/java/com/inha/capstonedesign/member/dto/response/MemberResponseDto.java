package com.inha.capstonedesign.member.dto.response;

import com.inha.capstonedesign.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private String memberEmail;
    private String memberName;
    private String memberNickName;
    private LocalDate memberBirthDate;
    private Integer memberAge;
    private String memberGender;
    private String memberRegion;
    private String memberImage;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(
                member.getMemberEmail(),
                member.getMemberName(),
                member.getMemberNickName(),
                member.getMemberBirthDate(),
                member.getMemberAge(),
                member.getMemberGender().getKorean(),
                member.getMemberRegion().getKorean(),
                member.getMemberImage().getImagePath());
    }
}
