package com.example.Ecommert.model.response;

import com.example.Ecommert.controller.ProductController;
import com.example.Ecommert.entity.Product;
import com.example.Ecommert.error.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice @ResponseStatus
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(ProductNotFoundException.class)
   public ResponseEntity<ErrorResponse> productNotFound(ProductNotFoundException exception) {
      return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND, false, exception.getMessage()));
   }
}
