package com.example.productManagement.products.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponseDto {
    private Long colorId;
    private String colorName;
    private List<SizeQuantityResponseDto> sizeQuantities;
}
