package com.example.internship.Controller;

import com.example.internship.Model.Feedback;
import com.example.internship.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*") // Allow requests from any origin for the frontend
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Get all feedback
    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Add new feedback
    @PostMapping
    public Feedback addFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackRepository.deleteById(id);
    }
// Get average rating
@GetMapping("/average-rating")
public Double getAverageRating() {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    return feedbacks.stream()
            .mapToDouble(Feedback::getRating)
            .average()
            .orElse(0.0);
}
    

}
