package com.example.productManagement.products.Dtos;

import com.example.productManagement.products.util.ColorNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorRequestDto {
    private ColorNames colorName;
    private List<SizeQuantityRequestDto> sizeQuantities;

    // Getters and setters
}
