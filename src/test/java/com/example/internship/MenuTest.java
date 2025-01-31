package com.example.internship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.internship.Model.Menu;

class MenuTest {

    @Test
    void testMenuEntity() {
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("Pizza");
        menu.setDescription("Delicious cheese pizza");
        menu.setPrice(9.99);
        menu.setInStock(true);
        menu.setImageUrl("https://example.com/pizza.jpg");

        assertEquals(1, menu.getId());
        assertEquals("Pizza", menu.getName());
        assertEquals("Delicious cheese pizza", menu.getDescription());
        assertEquals(9.99, menu.getPrice(), 0.01);
        assertTrue(menu.isInStock());
        assertEquals("https://example.com/pizza.jpg", menu.getImageUrl());
    }
}
