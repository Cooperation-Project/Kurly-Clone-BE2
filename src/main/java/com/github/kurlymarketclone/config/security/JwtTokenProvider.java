package com.github.kurlymarketclone.config.security;

import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${JWT_SECRET_KEY}")
    private String secretKeySource;
    private String secretKey;

    @PostConstruct
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString(secretKeySource.getBytes());
    }

    private long tokenValidMilliSeconds =1000L*60*60; //1시간

    public String createToken(String myId){
        Claims claims = Jwts.claims().setSubject(myId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMilliSeconds))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Token");
    }

    public boolean validToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        }catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails =userDetailsService.loadUserByUsername(getUserId(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserId(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
