package com.example.internship.Controller;

import com.example.internship.Model.Orders;
import com.example.internship.Repository.OrderRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order-success")
    public String orderSuccess(Model model) {
        List<Orders> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order-success";
    }

    // Get orders by date
    @GetMapping("/api/orders/by-date")
    @ResponseBody
    public ResponseEntity<List<Orders>> getOrdersByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp date) {
        // Calculate next day
        Timestamp nextDate = new Timestamp(date.getTime() + 86400000); // 24 hours in milliseconds
        List<Orders> orders = orderRepository.findOrdersByDate(date, nextDate);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 if no orders are found
        }
        return ResponseEntity.ok(orders);  // Return 200 with orders
    }

    // Get order count by date
    @GetMapping("/api/orders/count-by-date")
    @ResponseBody
    public Long getOrderCountByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp date) {
        Timestamp nextDate = new Timestamp(date.getTime() + 86400000); // Calculate the next date
        return orderRepository.countOrdersByDate(date, nextDate);
    }
}
