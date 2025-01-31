package com.example.internship.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders") // Renamed table to "orders" to avoid reserved keyword issue
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentIntentId; // Stripe Payment ID
    private double totalPrice;
    private Date orderDate;

    @Lob
    private String orderDetails; // JSON String to store cart items

    public Orders() {}

    public Orders(String paymentIntentId, double totalPrice, String orderDetails) {
        this.paymentIntentId = paymentIntentId;
        this.totalPrice = totalPrice;
        this.orderDate = new Date();
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

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }
}
