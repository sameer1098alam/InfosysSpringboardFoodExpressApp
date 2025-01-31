package com.example.internship;



import com.example.internship.Model.RestaurantDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDetailsTest {

    private RestaurantDetails restaurantDetails;

    @BeforeEach
    void setUp() {
        // Initializing RestaurantDetails before each test
        restaurantDetails = new RestaurantDetails();
    }

    @Test
    void testSetAndGetName() {
        // Set the name and check if it is correctly retrieved
        restaurantDetails.setName("Pizza Palace");
        assertEquals("Pizza Palace", restaurantDetails.getName(), "Restaurant name should be 'Pizza Palace'");
    }

    @Test
    void testSetAndGetDescription() {
        // Set the description and check if it is correctly retrieved
        restaurantDetails.setDescription("A cozy place for pizza lovers.");
        assertEquals("A cozy place for pizza lovers.", restaurantDetails.getDescription(), "Description should match the set value.");
    }

    @Test
    void testSetAndGetImageUrl() {
        // Set the image URL and check if it is correctly retrieved
        restaurantDetails.setImageUrl("https://example.com/image.jpg");
        assertEquals("https://example.com/image.jpg", restaurantDetails.getImageUrl(), "Image URL should match the set value.");
    }

    @Test
    void testSetAndGetIsOpen() {
        // Set the isOpen flag and check if it is correctly retrieved
        restaurantDetails.setIsOpen(true);
        assertTrue(restaurantDetails.isOpen(), "Restaurant should be open.");

        // Set the isOpen flag to false and check again
        restaurantDetails.setIsOpen(false);
        assertFalse(restaurantDetails.isOpen(), "Restaurant should be closed.");
    }

    @Test
    void testRestaurantDetailsInitialState() {
        // Test the default values of the object after creation (id should be null, other fields should be default values)
        assertNull(restaurantDetails.getId(), "ID should be null initially.");
        assertNull(restaurantDetails.getName(), "Name should be null initially.");
        assertNull(restaurantDetails.getDescription(), "Description should be null initially.");
        assertNull(restaurantDetails.getImageUrl(), "Image URL should be null initially.");
        assertFalse(restaurantDetails.isOpen(), "Restaurant should be closed by default.");
    }
}
