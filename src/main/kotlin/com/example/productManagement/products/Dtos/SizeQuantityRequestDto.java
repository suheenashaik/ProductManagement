package com.example.productManagement.products.Dtos;

import com.example.productManagement.products.util.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeQuantityRequestDto {
    private Sizes size;
    private Integer quantity;

    public SizeQuantityRequestDto(Sizes size) {
        this.size = size;
    }

    // Getters and setters
}
