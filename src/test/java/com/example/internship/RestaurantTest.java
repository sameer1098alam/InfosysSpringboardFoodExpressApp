package com.example.internship;

import com.example.internship.Model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    // ✅ Test successful restaurant creation with valid email and password
    @Test
    void testValidRestaurantCreation() {
        Restaurant restaurant = new Restaurant("test@example.com", "Password123");
        
        assertNotNull(restaurant);
        assertEquals("test@example.com", restaurant.getEmail());
        assertNotEquals("Password123", restaurant.getPassword()); // Password should be encrypted
    }

    // ✅ Test password encryption (hashed password should not be the same as plain text)
    @Test
    void testPasswordEncryption() {
        Restaurant restaurant = new Restaurant("test@example.com", "SecurePass1");
        
        assertNotEquals("SecurePass1", restaurant.getPassword()); // Ensure password is encrypted
        assertTrue(passwordEncoder.matches("SecurePass1", restaurant.getPassword())); // Check if it matches after encoding
    }

    // ✅ Test invalid password (should throw an exception)
    @Test
    void testInvalidPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Restaurant("test@example.com", "short");
        });

        assertEquals("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.", exception.getMessage());
    }

    // ✅ Test invalid email (should throw an exception)
    @Test
    void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Restaurant("invalid-email", "ValidPass1");
        });

        assertEquals("Invalid email format.", exception.getMessage());
    }

    // ✅ Test valid email formats
    @Test
    void testValidEmails() {
        assertDoesNotThrow(() -> new Restaurant("user@example.com", "ValidPass1"));
        assertDoesNotThrow(() -> new Restaurant("name.surname@domain.co", "ValidPass1"));
        assertDoesNotThrow(() -> new Restaurant("test123@company.org", "ValidPass1"));
    }

    // ✅ Test invalid email formats
    

    // ✅ Test valid passwords
    @Test
    void testValidPasswords() {
        assertDoesNotThrow(() -> new Restaurant("test@example.com", "StrongPass1"));
        assertDoesNotThrow(() -> new Restaurant("test@example.com", "AnotherPass2"));
    }

    // ✅ Test invalid passwords (less than 8 characters, missing uppercase, missing number)
    @Test
    void testInvalidPasswords() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant("test@example.com", "short"));
        assertThrows(IllegalArgumentException.class, () -> new Restaurant("test@example.com", "alllowercase1"));
        assertThrows(IllegalArgumentException.class, () -> new Restaurant("test@example.com", "ALLUPPERCASE1"));
        assertThrows(IllegalArgumentException.class, () -> new Restaurant("test@example.com", "NoNumber"));
    }
}
