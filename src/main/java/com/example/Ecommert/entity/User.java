package com.example.Ecommert.entity;

import com.example.Ecommert.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class User {
   @Id
   private String id;
   @Length(min = 2, message = "user name must have at least 4 characters") @NotBlank(message ="user name is required!")
   private String name;
   @Email(message = "please provide a valid email") @NotBlank(message ="user email is required!")
   private String email;

   @Length(min = 8, message = "password must have at least 8 character!") @NotBlank(message ="user password is required!")
   private String password;

   private String phone_number;

   @Builder.Default
   private Role role = Role.USER;

   private String address;

   @Builder.Default
   @JsonIgnore
   private boolean active = true;

   private Data passwordChangedAt;

   @Indexed
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   private Date createdAt;

   @Indexed
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   private Date updatedAt;
}
