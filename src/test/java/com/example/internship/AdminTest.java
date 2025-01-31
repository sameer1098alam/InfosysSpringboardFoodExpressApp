package com.example.internship;

import org.junit.jupiter.api.Test;

import com.example.internship.Model.Admin;

import static org.assertj.core.api.Assertions.assertThat;

class AdminTest {

    @Test
    void testAdminEntity() {
        // Create an instance of Admin
        Admin admin = new Admin();
        
        // Set values
        admin.setId(1L);
        admin.setUsername("adminUser");
        admin.setPassword("securePassword");

        // Verify values using AssertJ
        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getUsername()).isEqualTo("adminUser");
        assertThat(admin.getPassword()).isEqualTo("securePassword");
    }
}
