package com.example.chat_app.controller;

import com.example.chat_app.model.Question;
import com.example.chat_app.requests.CreateQuestionRequest;
import com.example.chat_app.requests.UpdateQuestionRequest;
import com.example.chat_app.service.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping(value = "/questions", consumes = {"application/json"})
    public ResponseEntity<Question> createQuestion(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateQuestionRequest request) {
        Question createdQuestion = questionService.createQuestion(jwt, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @GetMapping(value = "/questions")
    public ResponseEntity<List<Question>> getQuestions(@AuthenticationPrincipal Jwt jwt) {
        // get the list of questions from questionService
        List<Question> questions = questionService.getQuestions(jwt);
        // return the list of questions with status code 200
        return ResponseEntity.ok().body(questions);

    }

    @PatchMapping(value = "/questions/{questionId}")
    public ResponseEntity<Question> updateQuestion(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int questionId,
            @RequestBody UpdateQuestionRequest updateRequest) {
        Question updatedQuestion = questionService.updateQuestion(questionId, updateRequest);

        if (updatedQuestion != null) {
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/questions/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
