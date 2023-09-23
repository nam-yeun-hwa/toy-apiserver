package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5";

    /**
     * @function create
     * @description JWT라이브러리를 이용해 토큰을 생성
     * @param userEntity
     * @return
     */
    public String create(UserEntity userEntity){

        //기한(지금으로부터 1일)
        //토큰을 생성하는 과정에서 우리가 임의로 지정한 SECRET_KEY를 개인키로 사용 한다.
        Date expiraDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                //header > 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload > sub
                .setSubject(userEntity.getId())
                //payload > iss
                .setIssuer("demo app")
                //payload > iat
                .setIssuedAt(new Date())
                //payload > exp
                .setExpiration(expiraDate)
                .compact();

    }

    /**
     * @function validateAndGetUserId
     * @description 토큰 위조여부
     * @param token
     * @return
     */
    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교
                //위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
                .setSigningKey(SECRET_KEY)
                //parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱
                .parseClaimsJws(token)
                //userId가 필요하므로 getBody를 부른다.
                .getBody();

        return claims.getSubject();

    }



}
