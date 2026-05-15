package com.fincore.wallet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "THIS_IS_A_VERY_SECURE_SECRET_KEY_FOR_FINCORE_WALLET_SYSTEM";

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
//    private Key getSigningKey(){
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String extractEmail (String token){
//        Claims claims = Jwts.parser()
//                .verifyWith((javax.crypto.SecretKey) getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        return claims.getSubject();
//    }

    public String extractEmail (String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

}
