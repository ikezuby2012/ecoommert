package com.example.Ecommert.error;

import com.example.Ecommert.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@ResponseStatus
public class PasswordNotMatchExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(PasswordDoesNotMatchException.class)
   public ResponseEntity<ErrorResponse> passwordNotMatch(PasswordDoesNotMatchException exception) {
      return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND, false, exception.getMessage()));
   }
}
