package com.example.productManagement.products.controller;

import com.example.productManagement.products.service.ProductsService;
import com.example.productManagement.products.Dtos.ProductRequestDto;
import com.example.productManagement.products.Dtos.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping("/createProduct")
    public void createProduct(@RequestBody ProductRequestDto productReqDto) {
        this.productsService.createProduct(productReqDto);
    }

    @GetMapping("/getAllProducts")
    public List<ProductResponseDto> getAllProducts() {
        return this.productsService.getAllProducts();
    }

    @GetMapping("/getProductById/{productId}")
    public ProductResponseDto getProductById( @PathVariable Long productId) {
        return this.productsService.getProductById(productId);
    }
    @GetMapping("/getProductsByName/{productName}")
    public List<ProductResponseDto> getProductsByName( @PathVariable String productName){
       return this.productsService.getProductsByName(productName);
    }
    @GetMapping("/getProductsByCategory/{productCategory}")
    public List<ProductResponseDto> getProductsByCategory( @PathVariable String productCategory){
        return this.productsService.getProductsByCategory(productCategory);
    }
    @GetMapping("/getProductsByRelatedFor/{productRelatedFor}")
    public List<ProductResponseDto> getProductsByRelatedFor( @PathVariable String productRelatedFor){
        return this.productsService.getProductsByRelatedFor(productRelatedFor);
    }
    @GetMapping("/getProductsByBrand/{brand}")
    public List<ProductResponseDto> getProductsByBrand( @PathVariable String brand){
        return this.productsService.getProductsByBrand(brand);
    }


}
