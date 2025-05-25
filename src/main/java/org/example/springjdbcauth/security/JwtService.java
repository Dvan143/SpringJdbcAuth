package org.example.springjdbcauth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtService {
    String secret = "kdjsaldsJLKADJdalkwiorwqer2583409658019234971";
    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+360000)).signWith(getKey()).compact();
    }
    public String getUsernameByToken(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean isTokenExpired(String token){
        return new Date().after(Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getExpiration());
    }
    public boolean verifyToken(String token, String username){
        if(isTokenExpired(token)) return false;
        if(!username.equals(Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject())) return false;
        return true;
    }
    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
