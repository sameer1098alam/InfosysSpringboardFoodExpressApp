package com.example.internship.Repository;

import com.example.internship.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);  // Query to find admin by username
}
