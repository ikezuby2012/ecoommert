package com.example.Ecommert.implementation;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.error.PasswordDoesNotMatchException;
import com.example.Ecommert.model.Role;
import com.example.Ecommert.model.request.RegisterRequest;
import com.example.Ecommert.repository.UserRepository;
import com.example.Ecommert.service.UserService;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service @Slf4j @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public User createNewUser(User body) throws PasswordDoesNotMatchException {
      body.setPassword(passwordEncoder.encode(body.getPassword()));
      try {
         return userRepository.save(body);
      } catch (MongoWriteException e) {
         log.error("duplicate key");
         // a new method class to handle duplicate key should be created but
         // for now make we manage this now
         throw new PasswordDoesNotMatchException("email already exist");
      }
   }

   @Override
   public User findUserByEmail(String email) throws Exception {
      System.out.println("email is " + email);
      Optional<User> user = userRepository.findByEmail(email);

      if (user.isEmpty()) {
         throw new Exception("user does not exist");
      }
      return user.get();
   }

   @SneakyThrows
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = findUserByEmail(username);
      Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
      Role userRole = user.getRole();
      authority.add(new SimpleGrantedAuthority(userRole.toString()));
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authority);
   }
}
