package com.example.Ecommert.service;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.model.request.RegisterRequest;

public interface UserService {
   User createNewUser(User body);
}
