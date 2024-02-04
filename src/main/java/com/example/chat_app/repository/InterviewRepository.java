package com.example.chat_app.repository;

import com.example.chat_app.model.Interview;
import org.springframework.data.repository.CrudRepository;

public interface InterviewRepository extends CrudRepository<Interview, Long> {
    Interview findById(int id);
}
