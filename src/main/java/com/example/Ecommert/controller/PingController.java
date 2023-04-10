package com.example.Ecommert.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
   @GetMapping("/")
   public ResponseEntity<String> pingServer() {
      return ResponseEntity.ok().body("app is running");
   }
}
