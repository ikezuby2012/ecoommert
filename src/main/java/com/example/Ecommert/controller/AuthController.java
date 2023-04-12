package com.example.Ecommert.controller;

import com.example.Ecommert.DTO.UserDto;
import com.example.Ecommert.entity.User;
import com.example.Ecommert.error.PasswordDoesNotMatchException;
import com.example.Ecommert.model.Role;
import com.example.Ecommert.model.response.CustomResponse;
import com.example.Ecommert.model.response.SuccessLogin;
import com.example.Ecommert.repository.UserRepository;
import com.example.Ecommert.service.UserService;
import com.example.Ecommert.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController @RequestMapping("/api/v1/auth") @Slf4j
public class AuthController {
   @Autowired
   private UserService userService;
   @Autowired
   private JWTUtils jwtUtils;
   @Autowired
   private UserRepository userRepository;

   @PostMapping("/signup")
   public ResponseEntity<CustomResponse> registerUser(@Valid @RequestBody UserDto body) throws PasswordDoesNotMatchException {
      //check if password match
      if (!(body.getPassword().equals(body.getConfirmPassword()))) throw new PasswordDoesNotMatchException("passwords does not match");

      //map DTO to User entity
      User user = User.builder()
          .name(body.getName())
          .email(body.getEmail())
          .password(body.getPassword())
          .role(Role.USER)
          .build();
      User user1 = userService.createNewUser(user);
      return ResponseEntity.status(CREATED).body(new CustomResponse(CREATED, true, "user created successfully", 1, user1));
   }
   @GetMapping("/refresh")
   public ResponseEntity<SuccessLogin> getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String authHeader = request.getHeader("Authorization");
      String refresh_token = null, userName = null, userRole = null;
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
         refresh_token = authHeader.substring(7);
         try {
            Map<String, String> creds = jwtUtils.verifyToken(refresh_token);
            userName = creds.get("userName");

            User user = userRepository.findByEmail(userName).get();

            String access_token = jwtUtils.getAccessToken(user, request.getRequestURI());
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access-token", access_token);
            tokens.put("refresh-token", refresh_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return ResponseEntity.ok().body(new SuccessLogin(true, tokens, user));
         }catch (Exception e) {
            log.error("something went wrong {}", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
         }
      } throw new RuntimeException("refresh token is missing");
   }
}
