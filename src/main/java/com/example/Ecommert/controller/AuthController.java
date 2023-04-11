package com.example.Ecommert.controller;

import com.example.Ecommert.DTO.UserDto;
import com.example.Ecommert.entity.User;
import com.example.Ecommert.error.PasswordDoesNotMatchException;
import com.example.Ecommert.model.Role;
import com.example.Ecommert.model.request.LoginRequest;
import com.example.Ecommert.model.response.CustomResponse;
import com.example.Ecommert.service.UserService;
import com.example.Ecommert.utils.ValidPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController @RequestMapping("/api/v1/auth") @Slf4j
public class AuthController {
   @Autowired
   private UserService userService;

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
}
