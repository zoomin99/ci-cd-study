package com.inha.capstonedesign.member.dto.request;

import com.inha.capstonedesign.member.entity.Gender;
import com.inha.capstonedesign.member.entity.Member;
import com.inha.capstonedesign.member.entity.Region;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberRequestDto {

    @Getter
    public static class SignUp {


        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String memberEmail;
        @NotNull
        private String memberPassword;
        @NotNull
        private String memberName;
        @NotNull
        private String memberNickName;
        @NotNull
        private LocalDate memberBirthDate;
        @NotNull
        private String memberGender;
        @NotNull
        private String memberRegion;

        private Gender setEnumMemberGender(String gender) {
            if (gender.equals(Gender.MALE.getKorean())) {
                return Gender.MALE;
            } else {
                return Gender.FEMALE;
            }
        }

        private Region setEnumMemberRegion(String region) {
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

        public Member toEntity(String encryptedPassword) {
            return Member.builder()
                    .memberEmail(memberEmail)
                    .memberPassword(encryptedPassword)
                    .memberName(memberName)
                    .memberNickName(memberNickName)
                    .memberBirthDate(memberBirthDate)
                    .memberGender(setEnumMemberGender(memberGender))
                    .memberRegion(setEnumMemberRegion(memberRegion))
                    .build();
        }
    }

    @Getter
    public static class Login {


        //@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String memberEmail;

        private String memberPassword;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
        }
    }

    @Getter
    public static class Access {
        private String email;
        private List<String> roles = new ArrayList<>();

        private Access(String email, List<String> authorities) {
            this.email = email;
            roles.addAll(authorities);
        }

        public static Access from(String email, List<String> authorities) {
            return new Access(email, authorities);
        }
    }
}
