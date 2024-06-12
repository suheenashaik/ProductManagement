package com.example.productManagement.products.util;

import lombok.Data;

import java.util.List;
@Data
public class ProductReqDto {

    private String productName;
    private String productRelatedFor;
    private String productCategoryName;
    private String brand;
    private Double productMRP;
    private Double discount;
    private Long sellingPrice;
    private List<ColorDto> colors;

    // Getters and setters
}

