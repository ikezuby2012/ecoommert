package com.example.Ecommert.config;

import com.example.Ecommert.utils.CustomAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class AuditConfig {
   @Bean
   public AuditorAware<String> myAuditorProvider() {
      return new CustomAuditAware();
   }
}
