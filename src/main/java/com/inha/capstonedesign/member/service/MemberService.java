package com.inha.capstonedesign.member.service;

import com.inha.capstonedesign.auth.exception.AuthException;
import com.inha.capstonedesign.auth.exception.AuthExceptionType;
import com.inha.capstonedesign.member.dto.response.MemberResponseDto;
import com.inha.capstonedesign.member.entity.Member;
import com.inha.capstonedesign.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getMyInfo(String email) {
        final Member member = memberRepository.findByMemberWithImage(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        return MemberResponseDto.of(member);

    }
}
