package com.example.internship.controller;

import com.example.internship.Controller.HomeController;

import com.example.internship.Model.User;
import com.example.internship.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private HomeController homeController;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize mock objects and the controller
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
    }

    @Test
    void testHome() {
        // Test if the home page returns the correct view name
        String result = homeController.home();
        assertEquals("index", result);
    }

    @Test
    void testLoginPage() {
        // Test if the login page returns the correct view name
        String result = homeController.loginPage();
        assertEquals("login", result);
    }

    @Test
    void testSignupPage() {
        // Test if the signup page returns the correct view name
        String result = homeController.signupPage();
        assertEquals("signup", result);
    }

    @Test
    void testFeedback() {
        // Test if the feedback page returns the correct view name
        String result = homeController.feedback();
        assertEquals("feedback", result);
    }

    @Test
    void testViewProfileWhenLoggedIn() {
        // Mock session attribute for logged-in user
        when(session.getAttribute("loggedInUser")).thenReturn(testUser.getEmail());
        when(authService.getCurrentUser(testUser.getEmail())).thenReturn(testUser);

        // Test if profile page returns the correct view name
        String result = homeController.viewProfile(session, model);
        assertEquals("profile", result);
    }

    @Test
    void testViewProfileWhenNotLoggedIn() {
        // Mock session to simulate a user not logged in
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        // Test if not logged in user is redirected to login page
        String result = homeController.viewProfile(session, model);
        assertEquals("redirect:/login", result);
    }

    @Test
    void testUpdateProfile() {
        // Mock user update
        when(authService.updateUserProfile(testUser)).thenReturn(true);

        // Test if the profile update redirects to the profile page
        String result = homeController.updateProfile(testUser, model);
        assertEquals("redirect:/profile", result);
    }

    @Test
    void testUpdateProfileFailure() {
        // Mock failed user update
        when(authService.updateUserProfile(testUser)).thenReturn(false);

        // Test if the profile update fails and returns to the profile page with an error
        String result = homeController.updateProfile(testUser, model);
        assertEquals("profile", result);
        verify(model).addAttribute("error", "Profile update failed");
    }

    @Test
    void testDeactivateAccount() {
        // Mock account deactivation
        when(authService.deactivateUserByEmail(testUser.getEmail())).thenReturn(true);

        // Test if account deactivation redirects to the home page
        String result = homeController.deactivateAccount(testUser.getEmail(), model);
        assertEquals("Index", result);
    }

    @Test
    void testDeactivateAccountFailure() {
        // Mock failed account deactivation
        when(authService.deactivateUserByEmail(testUser.getEmail())).thenReturn(false);

        // Test if account deactivation fails and returns to the profile page with an error
        String result = homeController.deactivateAccount(testUser.getEmail(), model);
        assertEquals("profile", result);
        verify(model).addAttribute("error", "Account deactivation failed");
    }

    @Test
    void testAuthenticateUserSuccess() {
        // Mock successful authentication
        when(authService.authenticateUser(testUser.getEmail(), "password")).thenReturn(true);
        when(session.getAttribute("loggedInUser")).thenReturn(testUser.getEmail());

        // Test if login is successful and redirects to the home page
        String result = homeController.authenticate(testUser.getEmail(), "password", session, model);
        assertEquals("redirect:/home", result);
    }

    @Test
    void testAuthenticateUserFailure() {
        // Mock failed authentication
        when(authService.authenticateUser(testUser.getEmail(), "wrongPassword")).thenReturn(false);

        // Test if login fails and returns to the login page with an error
        String result = homeController.authenticate(testUser.getEmail(), "wrongPassword", session, model);
        assertEquals("login", result);
        verify(model).addAttribute("error", "Invalid email or password");
    }

    @Test
    void testRegisterUserSuccess() {
        // Mock successful registration
        when(authService.registerUser(testUser.getEmail(), "password")).thenReturn(true);

        // Test if user is successfully registered and redirected to login page
        String result = homeController.register(testUser.getEmail(), "password", "password", model);
        assertEquals("login", result);
        verify(model).addAttribute("message", "Signup successful! Please login.");
    }

    @Test
    void testRegisterUserFailure() {
        // Mock failed registration (user already exists)
        when(authService.registerUser(testUser.getEmail(), "password")).thenReturn(false);

        // Test if registration fails and returns to signup page with an error
        String result = homeController.register(testUser.getEmail(), "password", "password", model);
        assertEquals("signup", result);
        verify(model).addAttribute("error", "User already exists");
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        // Test if password mismatch results in error
        String result = homeController.register(testUser.getEmail(), "password", "differentPassword", model);
        assertEquals("signup", result);
        verify(model).addAttribute("error", "Passwords do not match");
    }
}
