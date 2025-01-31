package com.example.internship.Service;

import com.example.internship.Model.Restaurant;
import com.example.internship.Repository.RestaurantRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Check if restaurant already exists
    public boolean isRestaurantExists(String email) {
        return restaurantRepository.existsByEmail(email);
    }

    // Register a new restaurant
    public void registerRestaurant(String email, String password) {
        // Encrypt the password before saving it
        String encryptedPassword = passwordEncoder.encode(password);
        Restaurant restaurant = new Restaurant(email, encryptedPassword);
        restaurantRepository.save(restaurant);
    }

    // Authenticate restaurant during login
    public boolean authenticateRestaurant(String email, String password) {
        // Find the restaurant by email
        Restaurant restaurant = restaurantRepository.findByEmail(email);
        if (restaurant != null) {
            // Compare the entered password with the stored hashed password
            return passwordEncoder.matches(password, restaurant.getPassword());  // Using BCrypt to match
        }
        return false;
    }
 

}