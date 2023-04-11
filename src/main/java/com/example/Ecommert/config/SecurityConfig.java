package com.example.Ecommert.config;

import com.example.Ecommert.filter.CustomAuthFilter;
import com.example.Ecommert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private UserDetailsService userDetailsService;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      CustomAuthFilter customAuthFilter = new CustomAuthFilter(authenticationManagerBean(), userRepository);
      customAuthFilter.setFilterProcessesUrl("/api/v1/auth/login");
      http.csrf().disable();
      http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/product").authenticated();
      http.authorizeRequests().antMatchers("/api/v1/auth/**", "/api/v1/product/**").permitAll();
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//      http.addFilterAt(new CustomAuthFilter(userRepository), UsernamePasswordAuthenticationFilter.class);
      http.addFilter(customAuthFilter);
   }
   @Bean
   @Autowired
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
}
