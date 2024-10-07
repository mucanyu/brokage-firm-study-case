package com.mucanyu.brokage.auth.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

  private static final SecretKey SECRET_KEY =  Jwts.SIG.HS512.key().build();

  // Expire after an hour
  private static final long EXPIRATION_TIME = 1000L * 60L * 60L;

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SECRET_KEY)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      var claims = extractAllClaims(token);
      checkTokenExpired(claims);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private void checkTokenExpired(Claims claims) {
    if (claims.getExpiration().before(new Date())) {
      throw new IllegalArgumentException("Token expired");
    }
  }
}
