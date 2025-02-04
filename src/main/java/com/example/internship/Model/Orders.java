package com.example.internship.Model;

import jakarta.persistence.*;
import java.time.LocalDate;  // Use LocalDate instead of Date

@Entity
@Table(name = "orders") // Renamed table to avoid conflicts with SQL reserved keywords
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentIntentId;
    private double totalPrice;

    @Column(name = "order_date") // Adjust column name if necessary
    private LocalDate orderDate; // Use LocalDate instead of Date

    @Lob
    private String orderDetails;  // Store cart items as JSON string

    public Orders() {}

    public Orders(String paymentIntentId, double totalPrice, String orderDetails) {
        this.paymentIntentId = paymentIntentId;
        this.totalPrice = totalPrice;
        this.orderDate = LocalDate.now(); // Set the current date using LocalDate
        this.orderDetails = orderDetails;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }
}
