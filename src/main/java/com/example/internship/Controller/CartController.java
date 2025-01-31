package com.example.internship.Controller;

import com.example.internship.Model.Cart;
import com.example.internship.Model.Menu;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    private final Cart cart;

    // Injecting Stripe Secret Key from application.properties
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public CartController() {
        this.cart = new Cart();
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("menuItems", cart.getMenuItems());
        model.addAttribute("quantities", cart.getQuantities());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart"; // Directly returning the view name without .html extension
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("name") String name,
                            @RequestParam("price") double price,
                            @RequestParam("description") String description,
                            @RequestParam("inStock") boolean inStock,
                            @RequestParam("quantity") int quantity) {
        Menu menuItem = new Menu();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setDescription(description);
        menuItem.setInStock(inStock);

        cart.addMenuItem(menuItem, quantity);

        return "redirect:/cart"; // Redirect after adding to cart
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam("index") int index,
                                 @RequestParam("quantity") int quantity) {
        cart.updateMenuItemQuantity(index, quantity);
        return "redirect:/cart"; // Redirect to cart after updating quantity
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam("index") int index) {
        cart.removeMenuItem(index);
        return "redirect:/cart"; // Redirect to cart after removing item
    }

    /**
     * Endpoint to handle Stripe Payment
     */
    @PostMapping("/process-payment")
    @ResponseBody
    public Map<String, Object> processPayment(@RequestParam("paymentMethodId") String paymentMethodId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Calculate the total price in cents (Stripe requires the amount in the smallest currency unit)
            long amount = (long) (cart.getTotalPrice() * 100);

            // Create a PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("inr")
                    .setPaymentMethod(paymentMethodId)
                    .setConfirm(true) // Automatically confirm the payment
                    .build();

            // Set the secret key for Stripe API
            com.stripe.Stripe.apiKey = stripeSecretKey;

            // Create the PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Payment was successful
            response.put("status", "success");
            response.put("paymentIntentId", paymentIntent.getId());
        } catch (StripeException e) {
            // Handle payment failure
            response.put("status", "failure");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
