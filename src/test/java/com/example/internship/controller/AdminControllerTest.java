package com.example.internship.controller;


import com.example.internship.Controller.AdminController;
import com.example.internship.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        // Initialize mock objects and the controller
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdminLoginPage() {
        // Test if the admin login page returns the correct view name
        String result = adminController.adminLoginPage();
        assertEquals("admin-login", result);
    }

    @Test
    void testLogout() {
        // Test if logout redirects to the login page
        String result = adminController.logout();
        assertEquals("redirect:/admin-login", result);
    }

    @Test
    void testAuthenticateAdminSuccess() {
        // Mock successful admin authentication
        String username = "admin";
        String password = "admin123";
        when(adminService.authenticateAdmin(username, password)).thenReturn(true);

        // Test if the admin is redirected to the dashboard after successful authentication
        String result = adminController.authenticateAdmin(username, password, model);
        assertEquals("redirect:/admin-dashboard", result);
    }

    @Test
    void testAuthenticateAdminFailure() {
        // Mock failed admin authentication
        String username = "admin";
        String password = "wrongpassword";
        when(adminService.authenticateAdmin(username, password)).thenReturn(false);

        // Test if the admin login fails and the error message is set
        String result = adminController.authenticateAdmin(username, password, model);
        assertEquals("admin-login", result);
        verify(model).addAttribute("error", "Invalid username or password");
    }

    @Test
    void testAdminDashboard() {
        // Test if the admin dashboard page returns the correct view name
        String result = adminController.adminDashboard();
        assertEquals("admin-dashboard", result);
    }
}

