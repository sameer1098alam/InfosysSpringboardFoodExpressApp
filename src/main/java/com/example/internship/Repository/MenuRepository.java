package com.example.internship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.internship.Model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	Menu findByName(String name);
}
