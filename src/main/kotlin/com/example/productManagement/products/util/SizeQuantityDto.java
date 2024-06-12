package com.example.productManagement.products.util;

import lombok.Data;

@Data
public class SizeQuantityDto {
    private String size;
    private Integer quantity;

    public SizeQuantityDto(String size) {
        this.size = size;
    }

    // Getters and setters
}
