package com.example.Ecommert.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
   private HttpStatus statusCode;
   private Boolean status = false;
   private String message;
}
