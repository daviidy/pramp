package com.example.chat_app.service;

import com.example.chat_app.model.Question;
import com.example.chat_app.model.User;
import com.example.chat_app.requests.CreateQuestionRequest;
import com.example.chat_app.requests.UpdateQuestionRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface QuestionService {


    public List<Question> getPostsByUser(User user);

    public Question createQuestion(Jwt jwt, CreateQuestionRequest request);

    public List<Question> getQuestions(Jwt jwt);

    public Question updateQuestion(int questionId, UpdateQuestionRequest updateRequest);

    public void deleteQuestion(int questionId) throws Exception;
}
