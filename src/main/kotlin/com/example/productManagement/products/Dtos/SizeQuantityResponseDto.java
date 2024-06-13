package com.example.productManagement.products.Dtos;

import com.example.productManagement.products.util.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeQuantityResponseDto {
    private Long sizeQuantityId;
    private Sizes size;
    private Integer quantity;
    private Long colorId;
}
