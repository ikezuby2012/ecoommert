package com.example.Ecommert.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
   @Autowired
   private AuthenticationManager authenticationManager;

   public CustomAuthFilter(AuthenticationManager authenticationManagerBean) {
      this.authenticationManager = authenticationManagerBean;
   }

   @Override
   public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      System.out.println("inside filter");
      try {
         byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
         Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
         jsonRequest.forEach((key,v) -> {
            log.info(key + ", " + v);
         });

         String username = jsonRequest.get("username");
         String password = jsonRequest.get("password");
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return null;
   }
}
