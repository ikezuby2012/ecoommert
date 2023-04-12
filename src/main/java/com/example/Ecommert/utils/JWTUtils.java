package com.example.Ecommert.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Ecommert.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component @Data @NoArgsConstructor
public class JWTUtils {
   private final static String jwtSecret = "i-wish-i-can-fly-and-touch-the-sky-if-i-no-japa";
   @Value("${jwt.secret}")
   private String secretKey;

   // create Access_token
   public String getAccessToken(User user, String url) {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
      return JWT.create().withSubject(user.getEmail())
          .withExpiresAt(new Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000)))
          .withClaim("id", user.getId())
          .withIssuedAt(new Date())
          .withIssuer(url).withClaim("role", user.getRole().toString()).sign(algorithm);
   }
   // create Refresh Token
   public String getRefreshToken(User user, String url) {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
      return JWT.create().withSubject(user.getEmail())
          .withExpiresAt(new Date(System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000)))
          .withIssuer(url).sign(algorithm);
   }
   public Map<String, String> verifyToken(String token) {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
      JWTVerifier jwtVerifier = JWT.require(algorithm).build();
      DecodedJWT decodedJWT = jwtVerifier.verify(token);
      String userName = decodedJWT.getSubject();
      String userRole = decodedJWT.getClaim("role").asString();
      Map<String, String> creds = new HashMap<>();
      creds.put("userRole", userRole);
      creds.put("userName", userName);
      return creds;
   }
}
