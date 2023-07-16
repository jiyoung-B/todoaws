package com.example.todospringboot.security;

import com.example.todospringboot.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "ParisSaintGermain";

    // JWT 라이브러리를 이용해 JWT 토큰 생성
    // SECRET_KEY를 개인 키로 사용
    //

    public String create(UserEntity userEntity) {
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    // 토큰을 디코딩, 파싱 및 위조여부 확인
    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱.
        // 즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교.
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId가 필요하므로 getBody를 부른다.

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)

                .parseClaimsJws(token)
                    // "parseClaimsJwt"는 일반적인 JWT 형식의 토큰을 처리하고, 클레임 정보를 추출하는 반면
                    // "parseClaimsJws"는 JWS 형식의 토큰을 처리하고, 서명 검증을 수행하여 페이로드를 추출.
                .getBody(); // Claim 객체 반환 (=payload, body)
        return claims.getSubject(); // subject에 setting한 id 반환


    }

    public String create(final Authentication authentication) {
        ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .setSubject(userPrincipal.getName()) // id가 리턴됨.
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
