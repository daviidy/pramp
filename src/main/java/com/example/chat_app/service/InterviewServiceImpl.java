package com.example.chat_app.service;

import com.example.chat_app.model.Category;
import com.example.chat_app.model.Interview;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.CategoryRepository;
import com.example.chat_app.repository.InterviewRepository;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.requests.InterviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Interview createInterview(Jwt jwt, InterviewRequest request) throws Exception {
        Interview interview = new Interview();
        Category category = categoryRepository.findById(request.getCategoryId());
        // if category is not found, return exception with message 'category not found'
        if (category == null) {
            throw new Exception("Category not found");
        } else {
            interview.setCategory(category);
        }

        //if user is not found, return exception with message 'user not found'
        User author = this.getUserFromJwt(jwt);
        if (author == null) {
            throw new Exception("User not found");
        } else {
            interview.setAuthor(author);
        }

        //if peer user is not found, return exception with message 'peer user not found'
        User peer = userRepository.findById(request.getPeerId());
        if (peer == null) {
            throw new Exception("Peer user not found");
        } else {
            interview.setPeer(peer);
        }
        interview.setAuthor_feedback(request.getAuthorFeedback());
        interview.setPeer_feedback(request.getPeerFeedback());
        // Convert the date from MM-DD-YYYY to YYYY-MM-DD format
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = inputFormat.parse(request.getDate()); // Parse the input date string
        String formattedDate = outputFormat.format(parsedDate); // Format the date in the desired output format

        interview.setDate(formattedDate); // Set the correctly formatted date
        return interviewRepository.save(interview);
    }

    @Override
    public List<Interview> getInterviews(Jwt jwt) {
        User user = this.getUserFromJwt(jwt);
        return user != null ? (List<Interview>) interviewRepository.findAll() : List.of();
    }

    @Override
    public Interview showInterview(Jwt jwt, int interviewId) {
        User user = this.getUserFromJwt(jwt);
        Interview interview = interviewRepository.findById(interviewId);
        if(user != null && interview != null) {
            return interviewRepository.findById(interviewId);
        } else {
            return null;
        }
    }

    // update interview is rescheduling the interview
    @Override
    public Interview updateInterview(int interviewId, InterviewRequest request) {
        Interview interview = interviewRepository.findById(interviewId);
        if (interview != null) {
            if (request.getDate() != null) {
                interview.setDate(request.getDate());
            }
            return interviewRepository.save(interview);
        } else {
            return null;
        }
    }

    @Override
    public void deleteInterview(int interviewId) throws Exception {
        Interview interview = interviewRepository.findById(interviewId);
        if (interview != null) {
            interviewRepository.delete(interview);
        } else {
            throw new Exception("Interview not found");
        }
    }

    private User getUserFromJwt(Jwt jwt) {
        String username = jwt.getClaim("username");

        return userRepository.findByUsername(username);
    }
}
