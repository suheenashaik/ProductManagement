package com.example.productManagement.products.service;

import com.example.productManagement.products.repository.ColorRepository;
import com.example.productManagement.products.repository.ProductsRepository;
import com.example.productManagement.products.util.ProductReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductsService {

    @Autowired
    private ProductsRepository repository;
    @Autowired
    private ColorRepository colorRepository;


    public String addProduct(ProductReqDto dto) {

        return "Product added successfully";
    }
}
