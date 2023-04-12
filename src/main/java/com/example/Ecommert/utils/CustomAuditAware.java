package com.example.Ecommert.utils;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class CustomAuditAware implements AuditorAware<String> {
   @Autowired
   private UserRepository userRepository;

   @Override
   public Optional<String> getCurrentAuditor() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userEmail = authentication.getName();

      if (!authentication.isAuthenticated()) { return null; }
      User user = userRepository.findByEmail(userEmail).get();
//      System.out.println("user is " + user);
      return Optional.ofNullable(user.getEmail());
   }
}
