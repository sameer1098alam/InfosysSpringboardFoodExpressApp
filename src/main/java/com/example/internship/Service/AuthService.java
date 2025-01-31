package com.example.internship.Service;

import com.example.internship.Model.User;
import com.example.internship.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user
    public boolean registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return false; // User already exists
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword);
        userRepository.save(user);
        return true;
    }

    // Authenticate user
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false; // User not found
    }

    // Retrieve current user by email
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email);
    }

    // Update user profile
    public boolean updateUserProfile(User updatedUser) {
        User user = userRepository.findByEmail(updatedUser.getEmail());
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setContactInformation(updatedUser.getContactInformation());
            user.setDeliveryAddress(updatedUser.getDeliveryAddress());
            userRepository.save(user);
            return true;
        }
        return false; // User not found
    }

    // Deactivate user account
    public boolean deactivateUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(false);  // Set user as inactive
            userRepository.save(user);
            return true;
        }
        return false; // User not found
    }
}
