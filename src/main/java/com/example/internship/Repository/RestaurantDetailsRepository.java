package com.example.internship.Repository;

import com.example.internship.Model.RestaurantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {
}
