package com.example.Ecommert.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Persistable<String> {
   @Id
   private String id;

   @Length(min = 3)
   private String name;

   @NonNull  @NotBlank
   private int price;
   @NonNull  @NotBlank
   private String category;

   private String description;

   private String imageCover;
   private List<String> images;

   @Length(min = 1,max = 5, message = "rating must be less than 5 or more than 1")
   private double ratingAverage;

   @Indexed
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   @CreatedDate
   private Date createdAt = new Date();

   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   @LastModifiedDate
   private Date updatedAt = new Date();

   @Override
   public boolean isNew() {
      return false;
   }
}
