package com.example.internship.Model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // PasswordEncoder for hashing passwords
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructors
    public Restaurant() {}

    public Restaurant(String email, String password) {
        setEmail(email);      // Using setter to enforce validation
        setPassword(password);  // Using setter to enforce validation and encryption
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {   
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
        }
        this.password = passwordEncoder.encode(password); // Encrypting the password before saving
    }

    // Improved Password validation
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        if (!Pattern.compile(".*[a-z].*").matcher(password).find()) return false;
        if (!Pattern.compile(".*[A-Z].*").matcher(password).find()) return false;
        if (!Pattern.compile(".*\\d.*").matcher(password).find()) return false;
        return true;
    }

    // Improved Email validation
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        
        // Corrected regex for better email validation
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}
