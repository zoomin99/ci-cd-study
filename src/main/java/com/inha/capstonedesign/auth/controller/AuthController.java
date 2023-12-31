package com.inha.capstonedesign.auth.controller;

import com.inha.capstonedesign.auth.jwt.JwtTokenUtil;
import com.inha.capstonedesign.auth.jwt.dto.TokenDto;
import com.inha.capstonedesign.auth.jwt.exception.TokenException;
import com.inha.capstonedesign.auth.jwt.exception.TokenExceptionType;
import com.inha.capstonedesign.auth.service.AuthService;
import com.inha.capstonedesign.member.dto.request.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/members")
    public ResponseEntity<String> signUp(@RequestBody @Valid MemberRequestDto.SignUp signUp) {
        authService.signUp(signUp);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto.Login login, HttpServletResponse response) {

        TokenDto tokenDto = authService.login(login);

        final String refreshToken = tokenDto.getRefreshToken();
        final Long expiration = jwtTokenUtil.getExpiration(refreshToken);
        final Long expirationSecond = expiration / 1000;

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(expirationSecond.intValue());
//        refreshTokenCookie.setDomain("localhost");
//        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송
//        refreshTokenCookie.setPath("/"); // 경로 설정
        response.addCookie(refreshTokenCookie);


        return ResponseEntity.ok(tokenDto);
    }

//    @PostMapping("/reissue")
//    public ResponseEntity<String> reissueByCookie(
//            @CookieValue(value = "refreshToken", required = false) Cookie refreshCookie) {
//        if (refreshCookie == null) {
//            throw new TokenException(TokenExceptionType.REFRESH_TOKEN_NOT_EXIST);
//        }
//        String refreshToken = refreshCookie.getValue();
//        String reissuedAccessToken = authService.reissue(refreshToken);
//        return ResponseEntity.ok(reissuedAccessToken);
//    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(@RequestBody String refreshToken) {
        String reissuedAccessToken = authService.reissue(refreshToken);
        return ResponseEntity.ok(reissuedAccessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(value = "refreshToken", required = false) Cookie refreshCookie,
            HttpServletResponse response) {
        if (refreshCookie == null) {
            throw new TokenException(TokenExceptionType.REFRESH_TOKEN_NOT_EXIST);
        }
        String refreshToken = refreshCookie.getValue();

        authService.logout(refreshToken);

        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);

        return ResponseEntity.ok(null);
    }
}
