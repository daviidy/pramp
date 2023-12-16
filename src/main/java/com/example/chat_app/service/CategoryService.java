package com.example.chat_app.service;

import com.example.chat_app.model.Category;
import com.example.chat_app.model.Question;
import com.example.chat_app.model.User;
import com.example.chat_app.requests.CategoryRequest;
import com.example.chat_app.requests.CreateQuestionRequest;
import com.example.chat_app.requests.UpdateQuestionRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface CategoryService {

    public Category createCategory(Jwt jwt, CategoryRequest request);

    public List<Category> getCategories(Jwt jwt);

    public Category updateCategory(int categoryId, CategoryRequest request);

    public void deleteCategory(int categoryId) throws Exception;
}
