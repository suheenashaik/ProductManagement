package com.example.productManagement.products.repository;

import com.example.productManagement.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product,Long> {

}
