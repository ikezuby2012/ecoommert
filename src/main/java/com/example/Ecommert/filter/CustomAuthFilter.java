package com.example.Ecommert.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Ecommert.entity.User;
import com.example.Ecommert.implementation.UserServiceImpl;
import com.example.Ecommert.model.response.SuccessLogin;
import com.example.Ecommert.repository.UserRepository;
import com.example.Ecommert.service.UserService;
import com.example.Ecommert.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private UserRepository userRepository;

   public CustomAuthFilter(AuthenticationManager authenticationManagerBean, UserRepository userRepository) {
      this.authenticationManager = authenticationManagerBean;
      this.userRepository = userRepository;
   }


   @Override
   public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      System.out.println("inside filter");
      try {
         byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
         Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
         jsonRequest.forEach((key, v) -> {
            log.info(key + ", " + v);
         });

         String userEmail = jsonRequest.get("email");
         String password = jsonRequest.get("password");

         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);
         return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @SneakyThrows
   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
      UserDetails userDetails = (UserDetails) authResult.getPrincipal();
      JWTUtils jwtUtils = new JWTUtils();
      SuccessLogin successLogin;
      User user = userRepository.findByEmail(userDetails.getUsername()).get();

      String access_token = jwtUtils.getAccessToken(user, request.getRequestURI().toString());
      String refresh_token = jwtUtils.getRefreshToken(user, request.getRequestURI().toString());
      Map<String, String> tokens = new HashMap<>();
      tokens.put("access-token", access_token);
      tokens.put("refresh-token", refresh_token);

      successLogin = new SuccessLogin(true, tokens,user);

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), successLogin);
   }

}
