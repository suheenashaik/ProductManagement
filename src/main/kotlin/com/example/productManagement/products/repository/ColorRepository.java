package com.example.productManagement.products.repository;

import com.example.productManagement.products.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ColorRepository extends JpaRepository<Color, Integer> {
}
