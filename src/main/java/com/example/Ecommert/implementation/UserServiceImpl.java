package com.example.Ecommert.implementation;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.model.request.RegisterRequest;
import com.example.Ecommert.repository.UserRepository;
import com.example.Ecommert.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public User createNewUser(User body) {
      body.setPassword(passwordEncoder.encode(body.getPassword()));
      return userRepository.save(body);
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return null;
   }
}
