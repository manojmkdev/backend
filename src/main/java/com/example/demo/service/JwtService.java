package com.example.demo.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SystemUserRepo repo;

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.exp}")
    Long exp;

    public JwtService(SystemUserRepo repo) {
        this.repo = repo;
    }

    public String generateToken(String email) {
        SystemUser user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
       
        return Jwts.builder()
               .subject(email)
               .claim("role", user.getRole())
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + exp))
               .signWith(getSecretKey())
               .compact();
    }

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractAllClaim(String token){
          return Jwts.parser()
                 .verifyWith(getSecretKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims,T> reslover){
        return reslover.apply(extractAllClaim(token));
    }

    public String extractUsername(String token) {
         return extractClaim(token,Claims::getSubject);
    }


    public boolean validateToken(String token, UserDetails user) {
      String username = extractUsername(token);
      return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
