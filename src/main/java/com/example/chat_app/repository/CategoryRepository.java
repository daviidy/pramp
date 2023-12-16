package com.example.chat_app.repository;

import com.example.chat_app.model.Category;
import com.example.chat_app.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findById(int id);
}
