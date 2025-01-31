package com.example.internship.Controller;

import com.example.internship.Model.RestaurantDetails;
import com.example.internship.Service.RestaurantDetailsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RestaurantDetailsController {

    @Autowired
    private RestaurantDetailsService restaurantDetailsService;

    private final String UPLOAD_DIR = "E:\\\\Users\\\\Dell\\\\eclipse-workspace\\\\restaurant-uploads\\\\";

    @GetMapping("/restaurants/manage")
    public String showRestaurantForm(Model model) {
        model.addAttribute("restaurant", new RestaurantDetails());
        model.addAttribute("restaurants", restaurantDetailsService.getAllRestaurants());
        return "restaurant-manage";
    }

    @PostMapping("/save-restaurant")
    public String saveRestaurant(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam(value = "isOpen", defaultValue = "false") boolean isOpen) { // Capture the isOpen value

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file locally
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);

            // Save restaurant details including the "Open" status
            RestaurantDetails restaurant = new RestaurantDetails();
            restaurant.setName(name);
            restaurant.setDescription(description);
            restaurant.setImageUrl("/uploads/" + fileName); // Relative path for serving via static mapping
            restaurant.setIsOpen(isOpen);  // Set the open status
            restaurantDetailsService.saveRestaurant(restaurant);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/restaurants/manage";
    }

    
    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("restaurants", restaurantDetailsService.getAllRestaurants());
        return "home";
    }
}
