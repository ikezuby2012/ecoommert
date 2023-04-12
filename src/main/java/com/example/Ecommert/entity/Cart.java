package com.example.Ecommert.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cart {
   @Id
   private String id;

   @DBRef
   @CreatedBy
   private Product product;

   @DBRef
   private User user;

   @NonNull @NotBlank @Length(min = 1)
   private int quantity;

   @Indexed
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   @CreatedDate
   private Date createdAt;

   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   @LastModifiedDate
   private Date updatedAt;
}
