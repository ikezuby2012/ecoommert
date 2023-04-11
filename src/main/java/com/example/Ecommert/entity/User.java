package com.example.Ecommert.entity;

import com.example.Ecommert.model.Role;
import com.example.Ecommert.utils.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ValidPassword
public class User {
   @Id
   private String id;
   @Length(min = 2, message = "user name must have at least 4 characters") @NotBlank(message ="user name is required!")
   private String name;

   @Indexed(unique = true)
   @Email(message = "please provide a valid email") @NotBlank(message ="user email is required!")
   private String email;

   @JsonIgnore
   @Length(min = 8, message = "password must have at least 8 character!") @NotBlank(message ="user password is required!")
   private String password;

//   @Transient
//   @Length(min = 8, message = "password must have at least 8 character!") @NotBlank(message ="user password is required!")
//   private String confirmPassword;

   private String phone_number;

   @Builder.Default
   private Role role = Role.USER;

   private String address;

   @Builder.Default
   @JsonIgnore
   private boolean active = true;

   private Date passwordChangedAt;

   @Indexed
   @CreatedDate
   private Instant createdAt;

   @Indexed
   @LastModifiedDate
   private Instant updatedAt;
}