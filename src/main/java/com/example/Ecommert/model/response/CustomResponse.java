package com.example.Ecommert.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.security.PrivateKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {
   private HttpStatus statusCode;
   private boolean Status;
   private String message;
   private int length;
   private Object data;
}
