package com.example.Ecommert.controller;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.model.request.RegisterRequest;
import com.example.Ecommert.model.response.CustomResponse;
import com.example.Ecommert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
   @Autowired
   private UserService userService;

   @PostMapping("/signup")
   public ResponseEntity<CustomResponse> registerUser(@Valid @RequestBody User body) {
      User user = userService.createNewUser(body);
      return null;
   }
}
