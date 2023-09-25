package com.inha.capstonedesign.auth.service;

import com.inha.capstonedesign.auth.exception.AuthException;
import com.inha.capstonedesign.auth.exception.AuthExceptionType;
import com.inha.capstonedesign.auth.jwt.JwtTokenUtil;
import com.inha.capstonedesign.auth.jwt.dto.TokenDto;
import com.inha.capstonedesign.auth.jwt.exception.TokenException;
import com.inha.capstonedesign.auth.jwt.exception.TokenExceptionType;
import com.inha.capstonedesign.global.redis.RedisTemplateRepository;
import com.inha.capstonedesign.member.dto.request.MemberRequestDto;
import com.inha.capstonedesign.member.entity.Member;
import com.inha.capstonedesign.member.entity.Role;
import com.inha.capstonedesign.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisTemplateRepository redisTemplateRepository;

    @Transactional
    public void signUp(MemberRequestDto.SignUp signUp) {
        if (memberRepository.existsByMemberEmail(signUp.getMemberEmail())) {
            throw new AuthException(AuthExceptionType.EMAIL_ALREADY_EXISTS);
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(signUp.getMemberPassword());

        memberRepository.save(signUp.toEntity(encryptedPassword));
    }

    public TokenDto login(MemberRequestDto.Login login) {
        final Member member = memberRepository.findByMemberEmail(login.getMemberEmail())
                .orElseThrow(() -> new AuthException(AuthExceptionType.INVALID_EMAIL_OR_PASSWORD));

        if (!bCryptPasswordEncoder.matches(login.getMemberPassword(), member.getPassword())) {
            throw new AuthException(AuthExceptionType.INVALID_EMAIL_OR_PASSWORD);
        }

        TokenDto tokenDto = jwtTokenUtil.generateToken(member);

        final String refreshToken = tokenDto.getRefreshToken();
        final Long expiration = jwtTokenUtil.getExpiration(refreshToken);
        final Long expirationSecond = expiration / 1000;

        redisTemplateRepository.setDataWithExpiryMillis
                ("RT: " + member.getMemberEmail(), refreshToken, expiration);

        return tokenDto;
    }

    public String reissue(String refreshToken) {

        Claims claims = jwtTokenUtil.parseRefreshTokenClaims(refreshToken);
        String email = jwtTokenUtil.getEmail(claims);

        String refreshTokenInRedis = redisTemplateRepository.getData("RT: " + email);

        if (refreshTokenInRedis == null || !refreshTokenInRedis.equals(refreshToken)) {
            throw new TokenException(TokenExceptionType.INVALID_REFRESH_TOKEN);
        }

        final Member member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        return jwtTokenUtil.reissueAccessToken(member);
    }

    public void logout(String refreshToken) {

        Claims claims = jwtTokenUtil.parseRefreshTokenClaims(refreshToken);
        String email = jwtTokenUtil.getEmail(claims);

        redisTemplateRepository.deleteData("RT: " + email);

    }

    @Transactional
    public void upgradeAuthority(String memberEmail) {
        final Member member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new AuthException(AuthExceptionType.ACCOUNT_NOT_EXISTS));

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        member.setRoles(roles);
    }
}
