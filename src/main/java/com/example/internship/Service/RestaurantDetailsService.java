package com.example.internship.Service;

import com.example.internship.Model.RestaurantDetails;
import com.example.internship.Repository.RestaurantDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantDetailsService {

    @Autowired
    private RestaurantDetailsRepository repository;

    public RestaurantDetails saveRestaurant(RestaurantDetails restaurant) {
        return repository.save(restaurant);
    }

    public List<RestaurantDetails> getAllRestaurants() {
        return repository.findAll();
    }
}
