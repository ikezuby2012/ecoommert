package com.example.Ecommert.utils;

import org.springframework.context.annotation.Bean;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CheckPassword.class})
public @interface ValidPassword {
   String message()default "passwords does not match";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
