package com.example.Ecommert.error;

public class PasswordDoesNotMatchException extends Exception {
   public PasswordDoesNotMatchException() {
      super();
   }

   public PasswordDoesNotMatchException(String message) {
      super(message);
   }

   public PasswordDoesNotMatchException(String message, Throwable cause) {
      super(message, cause);
   }

   public PasswordDoesNotMatchException(Throwable cause) {
      super(cause);
   }

   protected PasswordDoesNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
