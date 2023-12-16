package com.example.chat_app.service;

import com.example.chat_app.model.Question;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.QuestionRepository;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.requests.CreateQuestionRequest;
import com.example.chat_app.requests.UpdateQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Question> getPostsByUser(User user) {
        return questionRepository.findByUser(user);
    }

    @Override
    public Question createQuestion(Jwt jwt, CreateQuestionRequest request) {
        User user = this.getUserFromJwt(jwt);
        request.setUserId(user.getId());
        Question question = new Question();
        question.setUser(user);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestions(Jwt jwt) {
        User user = this.getUserFromJwt(jwt);
        return user != null ? (List<Question>) questionRepository.findAll() : List.of();
    }

    @Override
    public Question updateQuestion(int questionId, UpdateQuestionRequest updateRequest) {
        Question question = questionRepository.findById(questionId);
        if (question != null) {
            if (updateRequest.getTitle() != null) {
                question.setTitle(updateRequest.getTitle());
            }
            if (updateRequest.getContent() != null) {
                question.setContent(updateRequest.getContent());
            }
            return questionRepository.save(question);
        } else {
            return null;
        }
    }

    @Override
    public void deleteQuestion(int questionId) throws Exception {
        Question question = questionRepository.findById(questionId);
        if (question != null) {
            questionRepository.delete(question);
        } else {
            throw new Exception("Question not found");
        }
    }

    private User getUserFromJwt(Jwt jwt) {
        String username = jwt.getClaim("username");

        return userRepository.findByUsername(username);
    }
}
