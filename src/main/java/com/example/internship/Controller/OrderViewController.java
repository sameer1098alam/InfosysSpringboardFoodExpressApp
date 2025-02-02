package com.example.internship.Controller;

import com.example.internship.Model.Orders;
import com.example.internship.Repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class OrderViewController {
    private final OrderRepository orderRepository;

    public OrderViewController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order-success")
    public String orderSuccess(Model model) {
        List<Orders> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order-success";
    }
    
    
}
