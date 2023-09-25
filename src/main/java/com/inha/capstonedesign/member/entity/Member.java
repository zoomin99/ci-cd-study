package com.inha.capstonedesign.member.entity;

import com.inha.capstonedesign.image.entity.MemberImage;
import com.inha.capstonedesign.voting.entity.VotingRecord;
import com.inha.capstonedesign.voting.entity.analysis.age.AgeGroup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_email")
    @NotNull
    private String memberEmail;

    @Column(name = "member_password")
    @NotNull
    private String memberPassword;

    @Column(name = "member_name")
    @NotNull
    private String memberName;

    @Column(name = "member_nickname")
    @NotNull
    private String memberNickName;

    @Column(name = "member_birth")
    @NotNull
    private LocalDate memberBirthDate;

    @Column(name = "member_age")
    @NotNull
    private Integer memberAge;

    @Column(name = "member_gender")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender memberGender;

    @Column(name = "member_Region")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Region memberRegion;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_image_id")
    private MemberImage memberImage;

    @OneToMany(mappedBy = "voter")
    private List<VotingRecord> votingRecords = new ArrayList<>();

    @Builder
    public Member(String memberEmail, String memberPassword, String memberName, String memberNickName, LocalDate memberBirthDate, Gender memberGender, Region memberRegion) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickName = memberNickName;
        this.memberBirthDate = memberBirthDate;

        LocalDate currentDate = LocalDate.now();
        this.memberAge = Period.between(memberBirthDate, currentDate).getYears();

        this.memberGender = memberGender;
        this.memberRegion = memberRegion;
        this.roles.add(Role.ROLE_USER);

        memberImage = MemberImage.createDefaultMemberImage();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public AgeGroup getAgeGroup() {
        if (this.memberAge < 20) {
            return AgeGroup.TEENS_OR_LESS;
        } else if (this.memberAge < 30) {
            return AgeGroup.TWENTIES;
        } else if (this.memberAge < 40) {
            return AgeGroup.THIRTIES;
        } else if (this.memberAge < 50) {
            return AgeGroup.FORTIES;
        } else if (this.memberAge < 60) {
            return AgeGroup.FIFTIES;
        } else if (this.memberAge < 70) {
            return AgeGroup.SIXTIES;
        } else {
            return AgeGroup.SEVENTIES_OR_ABOVE;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    //얘는 나중에 Authentication 가져올때 이거 사용하는지 확인해보자
    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
