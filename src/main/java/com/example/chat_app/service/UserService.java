package com.example.chat_app.service;
import com.example.chat_app.model.User;
import com.example.chat_app.requests.UserRegistrationRequest;

public interface UserService {
    User registerUser(UserRegistrationRequest userRegistrationRequest);
    User loginUser(String username, String password);
}
