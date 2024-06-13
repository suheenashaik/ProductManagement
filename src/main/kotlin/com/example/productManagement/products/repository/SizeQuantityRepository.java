package com.example.productManagement.products.repository;

import com.example.productManagement.products.entity.SizeQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeQuantityRepository extends JpaRepository<SizeQuantity, Integer> {

    @Query("select u from SizeQuantity u where u.color.colorId=?1")
    public List<SizeQuantity> findByColourId(Long colourId);
}
