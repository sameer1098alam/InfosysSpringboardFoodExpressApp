package com.example.internship.Controller;

import com.example.internship.Model.Menu;
import com.example.internship.Model.User;
import com.example.internship.Service.AuthService;
import com.example.internship.Service.MenuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private AuthService authService;
    
    @GetMapping("/")
    public String home() {
        return "index";  // Main homepage view
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Login page view
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";  // Signup page view
    }
    
    @GetMapping("/feedback")
    public String feedback() {
        return "feedback";  // Main homepage view
    }

    
       

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loggedInUser");

        if (email != null) {
            User user = authService.getCurrentUser(email);
            if (user != null) {
                model.addAttribute("user", user);
                return "profile";  // Show profile.html with user data
            } else {
                model.addAttribute("error", "User not found");
                return "error";
            }
        } else {
            // If no user is logged in, redirect to login page
            return "redirect:/login";
        }
        
    }



    // Update profile: Handle form submission for updating user details
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Model model) {
        boolean updated = authService.updateUserProfile(updatedUser);

        if (updated) {
            model.addAttribute("message", "Profile updated successfully");
            return "redirect:/profile";  // Redirect to profile page after update
        } else {
            model.addAttribute("error", "Profile update failed");
            return "profile";  // Return to profile page with error
        }
    }

    // Deactivate account: Handle account deactivation
    @PostMapping("/deactivateAccount")
    public String deactivateAccount(@RequestParam("email") String email, Model model) {
        boolean deactivated = authService.deactivateUserByEmail(email);

        if (deactivated) {
            return "Index";  // Log out after deactivation
        } else {
            model.addAttribute("error", "Account deactivation failed");
            return "profile";
        }
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String email, @RequestParam String password,
                               HttpSession session, Model model) {
        if (authService.authenticateUser(email, password)) {
            session.setAttribute("loggedInUser", email);  // Store email in session
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }


    // Registration logic (signup)
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password,
                           @RequestParam String confirmPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        boolean registered = authService.registerUser(email, password);
        if (!registered) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }
        model.addAttribute("message", "Signup successful! Please login.");
        return "login";
    }
}
