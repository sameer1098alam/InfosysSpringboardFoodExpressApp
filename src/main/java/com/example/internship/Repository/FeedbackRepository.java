package com.example.internship.Repository;



import com.example.internship.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double findAverageRating();
}

