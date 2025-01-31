package com.example.internship.Repository;

import com.example.internship.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  // This method is required to search users by email
    boolean existsByEmail(String email);  // This method checks if a user exists by email
}
