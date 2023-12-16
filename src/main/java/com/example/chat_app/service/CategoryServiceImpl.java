package com.example.chat_app.service;

import com.example.chat_app.model.Category;
import com.example.chat_app.model.Question;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.CategoryRepository;
import com.example.chat_app.repository.QuestionRepository;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.requests.CategoryRequest;
import com.example.chat_app.requests.CreateQuestionRequest;
import com.example.chat_app.requests.UpdateQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Category createCategory(Jwt jwt, CategoryRequest request) {
        Category category = new Category();
        category.setTitle(request.getTitle());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories(Jwt jwt) {
        User user = this.getUserFromJwt(jwt);
        return user != null ? (List<Category>) categoryRepository.findAll() : List.of();
    }

    @Override
    public Category updateCategory(int categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            if (request.getTitle() != null) {
                category.setTitle(request.getTitle());
            }
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCategory(int categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            categoryRepository.delete(category);
        } else {
            throw new Exception("Category not found");
        }
    }

    private User getUserFromJwt(Jwt jwt) {
        String username = jwt.getClaim("username");

        return userRepository.findByUsername(username);
    }
}
