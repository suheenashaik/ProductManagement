package com.example.productManagement.products.controller;

import com.example.productManagement.products.service.ProductsService;
import com.example.productManagement.products.util.ProductReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductReqDto product) {
       return this.productsService.addProduct(product);
    }


}
