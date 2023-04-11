package com.example.Ecommert.service;

import com.example.Ecommert.entity.User;
import com.example.Ecommert.error.PasswordDoesNotMatchException;

public interface UserService {
   User createNewUser(User body) throws PasswordDoesNotMatchException;
   User findUserByEmail(String email) throws Exception;
}
