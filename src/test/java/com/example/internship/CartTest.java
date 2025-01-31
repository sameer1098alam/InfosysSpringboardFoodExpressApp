package com.example.internship;

import com.example.internship.Model.Cart;
import com.example.internship.Model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Menu menuItem1;
    private Menu menuItem2;

    @BeforeEach
    void setUp() {
        // Initializing Cart and Menu items before each test
        cart = new Cart();
        menuItem1 = new Menu();
        menuItem1.setName("Pizza");
        menuItem1.setPrice(12.99);

        menuItem2 = new Menu();
        menuItem2.setName("Burger");
        menuItem2.setPrice(8.99);
    }

    @Test
    void testAddMenuItem() {
        cart.addMenuItem(menuItem1, 2); // 2 Pizzas
        cart.addMenuItem(menuItem2, 1); // 1 Burger

        assertEquals(2, cart.getMenuItems().size(), "Cart should contain two items.");
        assertEquals(2, cart.getQuantities().get(0), "Quantity of Pizza should be 2.");
        assertEquals(1, cart.getQuantities().get(1), "Quantity of Burger should be 1.");
    }

    @Test
    void testUpdateMenuItemQuantity() {
        cart.addMenuItem(menuItem1, 2); // 2 Pizzas
        cart.updateMenuItemQuantity(0, 3);

        assertEquals(3, cart.getQuantities().get(0), "Quantity of Pizza should be updated to 3.");
    }

    @Test
    void testRemoveMenuItem() {
        cart.addMenuItem(menuItem1, 2); // 2 Pizzas
        cart.addMenuItem(menuItem2, 1); // 1 Burger
        cart.removeMenuItem(1);

        assertEquals(1, cart.getMenuItems().size(), "Cart should contain one item after removal.");
        assertEquals("Pizza", cart.getMenuItems().get(0).getName(), "Remaining item should be Pizza.");
    }

    @Test
    void testGetTotalPrice() {
        cart.addMenuItem(menuItem1, 2); // 2 Pizzas
        cart.addMenuItem(menuItem2, 1); // 1 Burger

        double expectedTotal = (2 * 12.99) + (1 * 8.99);
        assertEquals(expectedTotal, cart.getTotalPrice(), "Total price should be correct.");
    }

    @Test
    void testClearCart() {
        cart.addMenuItem(menuItem1, 2); // 2 Pizzas
        cart.addMenuItem(menuItem2, 1); // 1 Burger
        cart.clearCart();

        assertTrue(cart.getMenuItems().isEmpty(), "Cart should be empty after clear.");
        assertTrue(cart.getQuantities().isEmpty(), "Quantities list should be empty after clear.");
    }
}
