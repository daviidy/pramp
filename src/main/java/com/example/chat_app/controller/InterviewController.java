package com.example.chat_app.controller;

import com.example.chat_app.model.Interview;
import com.example.chat_app.requests.InterviewRequest;
import com.example.chat_app.service.InterviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InterviewController {
    @Autowired
    private InterviewServiceImpl interviewService;

    @PostMapping(value = "/interviews", consumes = {"application/json"})
    public ResponseEntity<Interview> createInterview(@AuthenticationPrincipal Jwt jwt, @RequestBody InterviewRequest request) throws Exception {
        Interview interview = interviewService.createInterview(jwt, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(interview);
    }

    @GetMapping(value = "/interviews")
    public ResponseEntity<List<Interview>> getInterviews(@AuthenticationPrincipal Jwt jwt) {
        // get the list of interviews from interviewService
        List<Interview> interviews = interviewService.getInterviews(jwt);
        // return the list of interviews with status code 200
        return ResponseEntity.ok().body(interviews);

    }

    @GetMapping(value = "/interviews/{interviewId}")
    public ResponseEntity<Interview> showInterview(@AuthenticationPrincipal Jwt jwt,
                                                         @PathVariable int interviewId) {
        // get the list of interviews from interviewService
        Interview interview = interviewService.showInterview(jwt, interviewId);
        // return the list of interviews with status code 200
        return ResponseEntity.ok().body(interview);

    }

    @PatchMapping(value = "/interviews/{interviewId}")
    public ResponseEntity<Interview> updateInterview(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int interviewId,
            @RequestBody InterviewRequest updateRequest) {
        Interview updatedInterview = interviewService.updateInterview(interviewId, updateRequest);

        if (updatedInterview != null) {
            return ResponseEntity.ok(updatedInterview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/interviews/{interviewId}")
    public ResponseEntity<String> deleteInterview(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int interviewId) {
        try {
            interviewService.deleteInterview(interviewId);
            return new ResponseEntity<>("Interview deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
