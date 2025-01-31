package com.example.internship.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Menu> menuItems = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();

    public void addMenuItem(Menu menuItem, int quantity) {
        menuItems.add(menuItem);
        quantities.add(quantity);
    }

    public void updateMenuItemQuantity(int index, int quantity) {
        quantities.set(index, quantity);
    }

    public void removeMenuItem(int index) {
        menuItems.remove(index);
        quantities.remove(index);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < menuItems.size(); i++) {
            totalPrice += menuItems.get(i).getPrice() * quantities.get(i);
        }
        return totalPrice;
    }

    // Getter and Setter methods for menuItems and quantities
    public List<Menu> getMenuItems() {
        return menuItems;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void clearCart() {
        menuItems.clear();
        quantities.clear();
    }
}
