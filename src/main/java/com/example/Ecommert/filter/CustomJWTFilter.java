package com.example.Ecommert.filter;

import com.example.Ecommert.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component @Slf4j
public class CustomJWTFilter extends OncePerRequestFilter {
   @Autowired
   private JWTUtils jwtUtils;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String userName =null, token =null, userRole = null;
      String link = request.getServletPath();
      if (link.startsWith("/api/v1/auth") || link.equals("/api/v1/auth/refresh")) {
         filterChain.doFilter(request, response);
      } else {
         System.out.println("enter here");
         String authHeader = request.getHeader("Authorization");
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
               Map<String, String> creds = jwtUtils.verifyToken(token);
               userRole = creds.get("userRole");
               userName = creds.get("userName");
               Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
               authorities.add(new SimpleGrantedAuthority(userRole));

               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               filterChain.doFilter(request, response);
            } catch (Exception e) {
               log.error("something went wrong {}", e.getMessage());
               response.setStatus(HttpStatus.FORBIDDEN.value());
               Map<String, String> error = new HashMap<>();
               error.put("error_message", e.getMessage());
               response.setContentType(MediaType.APPLICATION_JSON_VALUE);
               new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
         } else {
            filterChain.doFilter(request, response);
         }
      }
   }
}
