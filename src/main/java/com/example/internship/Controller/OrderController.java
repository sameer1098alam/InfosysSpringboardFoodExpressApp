package com.example.internship.Controller;

import com.example.internship.Repository.OrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get order count for a specific date
    @GetMapping("/count-by-date")
    public long getOrderCountByDate(@RequestParam String date) {
        try {
            // Parse the input date (expected format: YYYY-MM-DD)
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return orderRepository.countOrdersByDate(localDate);
        } catch (Exception e) {
            // Return 0 in case of an error
            return 0;
        }
    }

    // Get weekly order count
    @GetMapping("/count-weekly")
    public long getOrderCountByWeek(@RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            LocalDate startOfWeek = localDate.with(java.time.DayOfWeek.MONDAY);
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            return orderRepository.countOrdersByWeek(startOfWeek, endOfWeek);
        } catch (Exception e) {
            return 0;
        }
    }

    @GetMapping("/count-monthly")
    public ResponseEntity<Long> getOrderCountByMonth(@RequestParam String yearMonth) {
        try {
            long count = orderRepository.countOrdersByMonth(yearMonth);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0L); // Handle errors gracefully
        }
    }


    // Get yearly order count
    @GetMapping("/count-yearly")
    public long getOrderCountByYear(@RequestParam int year) {
        try {
            // Calculate the first and last day of the year
            LocalDate startOfYear = LocalDate.of(year, 1, 1);
            LocalDate endOfYear = LocalDate.of(year, 12, 31);
            
            // Get the order count from the repository
            return orderRepository.countOrdersByYear(startOfYear, endOfYear);
        } catch (Exception e) {
            // Return 0 in case of an error
            return 0;
        }
    }
}
