package com.example.Ecommert.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Ecommert.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component @Data @NoArgsConstructor
public class JWTUtils {

   @Value("${jwt.secret}")
   private String secretKey;

   // create Access_token
   public String getAccessToken(User user, String url) {
      Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
      return JWT.create().withSubject(user.getName())
          .withExpiresAt(new Date(System.currentTimeMillis() + 10 + 60 + 1000))
          .withClaim("id", user.getId())
          .withIssuedAt(new Date())
          .withIssuer(url).withClaim("role", user.getRole().toString()).sign(algorithm);
   }
   // create Refresh Token
   public String getRefreshToken(User user, String url) {
      Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
      return JWT.create().withSubject(user.getName())
          .withExpiresAt(new Date(System.currentTimeMillis() + 30 + 60 + 1000))
          .withIssuer(url).sign(algorithm);
   }
   public String test() {
      return "testing";
   }
}
