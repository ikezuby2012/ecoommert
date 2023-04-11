package com.example.Ecommert.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {
   @Override
   public Optional<User> getCurrentAuditor() {
      return Optional.empty();
   }
}
