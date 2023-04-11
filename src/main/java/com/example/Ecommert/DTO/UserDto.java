package com.example.Ecommert.DTO;

import com.example.Ecommert.utils.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
   @Length(min = 2, message = "user name must have at least 4 characters") @NotBlank(message ="user name is required!")
   private String name;

   @Indexed(unique = true)
   @Email(message = "please provide a valid email") @NotBlank(message ="user email is required!")
   private String email;

   @Size(min = 8, message = "password must have at least 8 character!") @NotBlank(message ="user password is required!")
   private String password;

   @Size(min = 8, message = "password must have at least 8 character!") @NotBlank(message ="user password is required!")
   private String confirmPassword;
}
