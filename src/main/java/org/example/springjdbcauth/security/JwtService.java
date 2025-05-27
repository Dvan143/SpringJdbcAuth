package org.example.springjdbcauth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtService {
    String secretAccess = "kdjsaldsJLKADJdalkFDGKSFsdl;wiorwqer2583409658019234971";
    String secretRefresh = "JFDeERUOIIJXDFLSflxsdjfdXDLJKSuuy234892378478923";

    // Access
    public String generateAccessToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+1000*60*120)).signWith(getAccessKey()).compact();
    }

    public String getUsernameByAccessToken(String token){
        return Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenAccessExpired(String token){
        return new Date().after(Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getExpiration());
    }
    public boolean verifyAccessToken(String token, String username){
        if(isTokenAccessExpired(token)) return false;
        if(!username.equals(Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getSubject())) return false;
        return true;
    }

    // Refresh
    public String generateRefreshToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+1000*60*60*24*2)).signWith(getRefreshKey()).compact();
    }
    public String getUsernameByRefreshToken(String token){
        return Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenRefreshExpired(String token){
        return new Date().after(Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getExpiration());
    }
    public boolean verifyRefreshToken(String token, String username){
        if(isTokenAccessExpired(token)) return false;
        if(!username.equals(Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getSubject())) return false;
        return true;
    }
    public SecretKey getAccessKey(){
        return Keys.hmacShaKeyFor(secretAccess.getBytes(StandardCharsets.UTF_8));
    }
    public SecretKey getRefreshKey(){
        return Keys.hmacShaKeyFor(secretAccess.getBytes(StandardCharsets.UTF_8));
    }
}
