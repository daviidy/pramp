package com.example.chat_app.repository;

import com.example.chat_app.model.Question;
import com.example.chat_app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findByUser(User user);

    Question findById(int id);
}
