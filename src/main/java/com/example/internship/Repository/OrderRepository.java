package com.example.internship.Repository;

import com.example.internship.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate >= :date AND o.orderDate < :nextDate")
    Long countOrdersByDate(@Param("date") Timestamp date, @Param("nextDate") Timestamp nextDate);

    @Query("SELECT o FROM Orders o WHERE o.orderDate >= :date AND o.orderDate < :nextDate")
    List<Orders> findOrdersByDate(@Param("date") Timestamp date, @Param("nextDate") Timestamp nextDate);
}
