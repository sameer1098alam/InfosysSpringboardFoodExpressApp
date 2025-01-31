package com.example.internship.Service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final String ADMIN_USERNAME = "admin@gmail.com";  // Fixed admin username
    private final String ADMIN_PASSWORD = "123";  // Fixed admin password

    // Authenticate admin using fixed username and password
    public boolean authenticateAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
