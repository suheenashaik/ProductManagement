package com.example.productManagement.products.Dtos;

import com.example.productManagement.products.util.Category;
import com.example.productManagement.products.util.ProductRelatedFor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private String productName;
    private Category productCategory;
    private ProductRelatedFor productRelatedFor;
    private String brand;
    private Double productMRP;
    private Double discount;
    private Long sellingPrice;
    private List<ColorRequestDto> colors;

    // Getters and setters
}

