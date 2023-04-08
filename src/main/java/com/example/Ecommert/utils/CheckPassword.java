package com.example.Ecommert.utils;

import com.example.Ecommert.model.request.RegisterRequest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CheckPassword implements ConstraintValidator<ValidPassword, RegisterRequest> {

   @Override
   public boolean isValid(RegisterRequest user, ConstraintValidatorContext context) {
      if (user.getPassword() == null || user.getConfirmPassword() == null) return false;

      return user.getPassword().equals(user.getConfirmPassword());
   }
}
