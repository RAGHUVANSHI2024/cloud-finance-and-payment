package com.finance.service;
import com.finance.dto.LoginRequest;
import com.finance.dto.RegisterRequest;
import com.finance.model.User;

public interface UserService {

   User registerUser(RegisterRequest user);

   User findByUsername(String username);

   User login(LoginRequest loginRequest);


    
}
