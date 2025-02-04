package com.example.internship.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import com.example.internship.Model.Menu;
import com.example.internship.Service.MenuService;

@Controller
@RequestMapping("/menu") 
public class MenuController {

    @Autowired
    private MenuService menuService;

    private final String UPLOAD_DIR = "E:\\\\Users\\\\Dell\\\\eclipse-workspace\\\\menu-uploads\\\\"; // Upload directory

    @GetMapping
    public String menuPage(Model model) {
        List<Menu> menuItems = menuService.getAllMenuItems();
        model.addAttribute("menuItems", menuItems);
        return "menu"; // menu.html template
    }
    @GetMapping("/view")
    public String viewMenu(Model model) {
        List<Menu> menuItems = menuService.getAllMenuItems();
        System.out.println("Menu Items Retrieved: " + menuItems); // Debugging
        model.addAttribute("menuItems", menuItems);
        return "view-menu";
    }



    @PostMapping("/save-menu")
    public String saveMenu(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam(value = "inStock", required = false, defaultValue = "false") Boolean inStock, // Handle unchecked case
                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
    
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);
    
            Menu menu = new Menu();
            menu.setName(name);
            menu.setDescription(description);
            menu.setPrice(price);
            menu.setInStock(inStock != null && inStock); // Ensures it's false if null
            menu.setImageUrl("/menu-uploads/" + fileName);  
    
            menuService.addOrUpdateMenuItem(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return "redirect:/menu";
    }

    // Edit menu item
    @GetMapping("/edit/{id}")
    public String editMenuItem(@PathVariable int id, Model model) {
        Menu menu = menuService.getMenuItemById(id);
        model.addAttribute("menu", menu);
        return "edit-menu"; // This would be an edit menu page template
    }

    @PostMapping("/update-menu/{id}")
    public String updateMenuItem(@PathVariable int id, 
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") double price,  // Added price field
                                 @RequestParam(value = "inStock", required = false, defaultValue = "false") Boolean inStock, // 
                                 @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Menu menu = menuService.getMenuItemById(id);
            menu.setName(name);
            menu.setDescription(description);
            menu.setPrice(price);  // Set the price
            menu.setInStock(inStock != null && inStock);  // Set the inStock status

            // Handle image update if a new file is uploaded
            if (!imageFile.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath);
                menu.setImageUrl("/menu-uploads/" + fileName);
            }

            menuService.addOrUpdateMenuItem(menu);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/menu";
    }

    // Delete menu item
    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable int id) {
        menuService.deleteMenuItem(id);
        return "redirect:/menu";
    }
}


/**
 * REST API for Menu Management
 */
@RestController
@RequestMapping("/api/menu")
class MenuApiController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public Menu getMenuItemById(@PathVariable int id) {
        return menuService.getMenuItemById(id);
    }

    @PostMapping
    public Menu addMenuItem(@RequestBody Menu menu) {
        return menuService.addOrUpdateMenuItem(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenuItem(@PathVariable int id, @RequestBody Menu menu) {
        menu.setId(id);
        return menuService.addOrUpdateMenuItem(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable int id) {
        menuService.deleteMenuItem(id);
    }
}
