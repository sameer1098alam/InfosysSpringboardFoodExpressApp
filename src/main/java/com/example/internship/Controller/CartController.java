package com.example.internship.Controller;

import com.example.internship.Model.Cart;
import com.example.internship.Model.Menu;
import com.example.internship.Model.Orders;
import com.example.internship.Repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final OrderRepository orderRepository;

    // Injecting Stripe Secret Key from application.properties
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public CartController(OrderRepository orderRepository) {
        this.cart = new Cart();
        this.orderRepository = orderRepository;
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

   
    @PostMapping("/process-payment")
    @ResponseBody
    public Map<String, Object> processPayment(@RequestParam("paymentMethodId") String paymentMethodId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Set Stripe API Key
            com.stripe.Stripe.apiKey = stripeSecretKey;

            long amount = (long) (cart.getTotalPrice() * 100);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("inr")
                    .setPaymentMethod(paymentMethodId)
                    .setConfirm(true)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Convert cart items to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String orderDetailsJson = objectMapper.writeValueAsString(cart.getMenuItems());

            // Save order in database
            Orders order = new Orders(paymentIntent.getId(), cart.getTotalPrice(), orderDetailsJson);
            orderRepository.save(order);

            // Clear the cart after successful payment
            cart.clearCart();

            response.put("status", "success");
            response.put("paymentIntentId", paymentIntent.getId());
        } catch (StripeException | com.fasterxml.jackson.core.JsonProcessingException e) {
            response.put("status", "failure");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
