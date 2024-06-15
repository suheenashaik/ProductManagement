package com.example.productManagement.products.repository;

import com.example.productManagement.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product,Long> {

    @Query("SELECT u FROM Product u WHERE LOWER(u.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    List<Product> findByProductName(@Param("productName") String productName);

    @Query("SELECT u FROM Product u WHERE UPPER(u.productCategory) = UPPER(:productCategory)")
    List<Product> findByProductCategory(@Param("productCategory") String productCategory);

    @Query("SELECT u FROM Product u WHERE UPPER(u.productRelatedFor) = UPPER(:ProductRelatedFor)")
    List<Product> findProductsByRelatedFor(@Param("ProductRelatedFor") String ProductRelatedFor);

    @Query("SELECT u FROM Product u WHERE LOWER(u.brand) LIKE LOWER(CONCAT('%', :brandName, '%'))")
    List<Product> findProductsByBrand(@Param("brandName") String brandName);


}
