package com.inha.capstonedesign.member.controller;

import com.inha.capstonedesign.member.dto.request.MemberRequestDto;
import com.inha.capstonedesign.member.dto.response.MemberResponseDto;
import com.inha.capstonedesign.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<MemberResponseDto> getMyInfo(@AuthenticationPrincipal MemberRequestDto.Access access) {
        return ResponseEntity.ok(memberService.getMyInfo(access.getEmail()));
    }
}
