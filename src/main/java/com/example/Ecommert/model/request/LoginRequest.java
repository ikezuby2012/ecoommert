package com.example.Ecommert.model.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
   @NotBlank(message = "email is required") @Email @NonNull
   private String email;
   @NonNull @NotBlank(message = "password is required")
   private String password;
}
