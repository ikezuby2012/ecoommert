package com.example.Ecommert.model.response;

import com.example.Ecommert.entity.User;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor
public class SuccessLogin {
   private boolean status = true;
   private Map<String, String> tokens;
   private User data;
}
