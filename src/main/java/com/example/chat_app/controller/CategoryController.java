package com.example.chat_app.controller;

import com.example.chat_app.model.Category;
import com.example.chat_app.requests.CategoryRequest;
import com.example.chat_app.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping(value = "/categories", consumes = {"application/json"})
    public ResponseEntity<Category> createCategory(@AuthenticationPrincipal Jwt jwt, @RequestBody CategoryRequest request) {
        Category category = categoryService.createCategory(jwt, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> getCategories(@AuthenticationPrincipal Jwt jwt) {
        // get the list of categories from categoryService
        List<Category> categories = categoryService.getCategories(jwt);
        // return the list of categories with status code 200
        return ResponseEntity.ok().body(categories);

    }

    @PatchMapping(value = "/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int categoryId,
            @RequestBody CategoryRequest updateRequest) {
        Category updatedCategory = categoryService.updateCategory(categoryId, updateRequest);

        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
