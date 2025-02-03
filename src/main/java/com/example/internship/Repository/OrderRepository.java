package com.example.internship.Repository;

import com.example.internship.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

<<<<<<< HEAD
import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate >= :date AND o.orderDate < :nextDate")
    Long countOrdersByDate(@Param("date") Timestamp date, @Param("nextDate") Timestamp nextDate);

    @Query("SELECT o FROM Orders o WHERE o.orderDate >= :date AND o.orderDate < :nextDate")
    List<Orders> findOrdersByDate(@Param("date") Timestamp date, @Param("nextDate") Timestamp nextDate);
=======
import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    // Daily Orders count
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate = :date")
    long countOrdersByDate(@Param("date") LocalDate date);

    // Weekly Orders count
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate BETWEEN :startOfWeek AND :endOfWeek")
    long countOrdersByWeek(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);

    @Query("select count(o.id) from Orders o where function('date_format', o.orderDate, '%Y-%m') = :yearMonth")
    Long countOrdersByMonth(@Param("yearMonth") String yearMonth);



    // Yearly Orders count - Use start and end of year
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate BETWEEN :startOfYear AND :endOfYear")
    long countOrdersByYear(@Param("startOfYear") LocalDate startOfYear, @Param("endOfYear") LocalDate endOfYear);
>>>>>>> 8f054d13173b64348e4cc7a3582240026e26a8a0
}
