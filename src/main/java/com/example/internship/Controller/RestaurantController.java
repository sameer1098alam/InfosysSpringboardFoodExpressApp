package com.example.internship.Controller;

import com.example.internship.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Show Signup Page
    @GetMapping("/restaurant-signup")
    public String showSignupPage() {
        return "restaurant-signup";  // View for restaurant signup page
    }
    
    @GetMapping("/restaurant-login")
    public String showLoginPage() {
    	return "restaurant-login";
    }
    
    @GetMapping("/restaurant-dashboard")
    public String showDashboard() {
        return "restaurant-dashboard";  // This will map to restaurant-dashboard.html in templates folder
    }


    // Handle Signup Logic
    @PostMapping("/restaurant-register")
    public String register(@RequestParam String email, @RequestParam String password,
                           @RequestParam String confirmPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "restaurant-signup";  // Return to signup page if passwords don't match
        }
        
        // Check if restaurant already exists
        if (restaurantService.isRestaurantExists(email)) {
            model.addAttribute("error", "Restaurant already exists with this email");
            return "restaurant-signup";  // Return to signup page if restaurant already exists
        }

        // Register the restaurant
        restaurantService.registerRestaurant(email, password);
        model.addAttribute("message", "Signup successful! Please login.");
        return "restaurant-login";  // Redirect to login page after successful signup
    }

    @PostMapping("/restaurant-authenticate")
    public String authenticate(@RequestParam String email, @RequestParam String password,
                               HttpSession session, Model model) {
        if (restaurantService.authenticateRestaurant(email, password)) {
            session.setAttribute("loggedInRestaurant", email);
            return "redirect:/restaurant-dashboard";  // Redirect to dashboard
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "restaurant-login";  // Return to login page
        }
    }
    



    }

