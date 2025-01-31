package com.example.internship.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.internship.Model.Menu;
import com.example.internship.Repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenuItems() {
        return menuRepository.findAll();
    }

    public Menu getMenuItemById(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu addOrUpdateMenuItem(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteMenuItem(int id) {
        menuRepository.deleteById(id);
        
    }

 // Sample method to get a specific menu item by name
    public Menu getMenuItemByName(String name) {
        return menuRepository.findByName(name);  // This will now work
    }
    
    public Menu addToCart(Menu menu) {
        return menuRepository.save(menu);
    }
}
    

