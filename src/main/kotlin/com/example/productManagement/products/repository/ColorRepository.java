package com.example.productManagement.products.repository;

import com.example.productManagement.products.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(" select  u  from Color u where u.product.productId=?1")
    public List<Color> findByProductId(Long  productId);
}
