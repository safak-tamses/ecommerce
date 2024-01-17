package com.x.ecommerceback.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtProvider {
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication auth){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 846000000))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
    }
    public String getEmailFromToken(String jwt){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt.substring(7)).getBody();
        return String.valueOf(claims.get("email"));
    }
}
