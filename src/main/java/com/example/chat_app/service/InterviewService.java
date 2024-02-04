package com.example.chat_app.service;

import com.example.chat_app.model.Interview;
import com.example.chat_app.requests.InterviewRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface InterviewService {
    public Interview createInterview(Jwt jwt, InterviewRequest request) throws Exception;
    public List<Interview> getInterviews(Jwt jwt);

    Interview showInterview(Jwt jwt, int interviewId);

    public Interview updateInterview(int interviewId, InterviewRequest request);
    public void deleteInterview(int interviewId) throws Exception;
}
