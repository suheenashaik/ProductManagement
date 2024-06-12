package com.example.productManagement.products.util;

import lombok.Data;

import java.util.List;
@Data
public class ColorDto {
    private String colorName;
    private List<SizeQuantityDto> sizeQuantities;

    // Getters and setters
}
