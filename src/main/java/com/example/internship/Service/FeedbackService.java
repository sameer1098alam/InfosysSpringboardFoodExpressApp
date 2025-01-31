package com.example.internship.Service;

import com.example.internship.Model.Feedback;
import com.example.internship.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

   

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}
