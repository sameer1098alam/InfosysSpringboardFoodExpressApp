package com.example.internship.Controller;

import com.example.internship.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin login page
    @GetMapping("/admin-login")
    public String adminLoginPage() {
        return "admin-login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/admin-login";  // Redirect to login page after logging out
    }


    // Handle admin authentication
    @PostMapping("/admin-authenticate")
    public String authenticateAdmin(@RequestParam String username, @RequestParam String password, Model model) {
        if (adminService.authenticateAdmin(username, password)) {
            return "redirect:/admin-dashboard";  // Redirect to admin dashboard on successful login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login";  // Show error message if authentication fails
        }
    }

    // Admin dashboard page
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";  // This page will be displayed after successful login
    }
}
