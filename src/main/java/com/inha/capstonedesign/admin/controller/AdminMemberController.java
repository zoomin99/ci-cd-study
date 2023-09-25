package com.inha.capstonedesign.admin.controller;

import com.inha.capstonedesign.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/members")
public class AdminMemberController {

    private final AuthService authService;

    @PostMapping("/authority/{memberEmail}")
    public ResponseEntity<String> upgradeAuthority(@PathVariable String memberEmail) {
        authService.upgradeAuthority(memberEmail);

        return ResponseEntity.ok(null);
    }
}
