package com.example.internship.Repository;

import com.example.internship.Model.Restaurant;



import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByEmail(String email);
    Restaurant findByEmail(String email);
    

}
