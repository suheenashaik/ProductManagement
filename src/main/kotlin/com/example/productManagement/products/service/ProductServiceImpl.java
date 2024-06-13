package com.example.productManagement.products.service;

import com.example.productManagement.products.Dtos.ColorResponseDto;
import com.example.productManagement.products.Dtos.ProductResponseDto;
import com.example.productManagement.products.Dtos.SizeQuantityResponseDto;
import com.example.productManagement.products.entity.Color;
import com.example.productManagement.products.entity.Product;
import com.example.productManagement.products.entity.SizeQuantity;
import com.example.productManagement.products.repository.ColorRepository;
import com.example.productManagement.products.repository.ProductsRepository;
import com.example.productManagement.products.repository.SizeQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl {

    @Autowired
    private ProductsRepository productRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeQuantityRepository sizeQuantityRepository;


    public List<ProductResponseDto> streamProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    ProductResponseDto convertToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductRelatedFor(product.getProductRelatedFor());
        dto.setProductCategory(product.getProductCategory());
        dto.setBrand(product.getBrand());
        dto.setProductMRP(product.getProductMRP());
        dto.setDiscount(product.getDiscount());
        dto.setSellingPrice(product.getSellingPrice());

        List<ColorResponseDto> colorDtos = new ArrayList<>();
        List<Color> colors=this.colorRepository.findByProductId(product.getProductId());
        for (Color color : colors) {
            ColorResponseDto colorDto = convertColorToDto(color);
            colorDtos.add(colorDto);
        }
        dto.setColors(colorDtos);

        return dto;
    }

    private ColorResponseDto convertColorToDto(Color color) {
        ColorResponseDto dto = new ColorResponseDto();
        dto.setColorId(color.getColorId());
        dto.setColorName(color.getColorName().toString());

        List<SizeQuantityResponseDto> sizeQuantityDtos = new ArrayList<>();
        List<SizeQuantity> sizesQuantities=this.sizeQuantityRepository.findByColourId(color.getColorId());
        for (SizeQuantity sizeQuantity : sizesQuantities) {
            SizeQuantityResponseDto sizeQuantityDto = convertSizeQuantityToDto(sizeQuantity);
            sizeQuantityDtos.add(sizeQuantityDto);
        }
        dto.setSizeQuantities(sizeQuantityDtos);

        return dto;
    }

    private SizeQuantityResponseDto convertSizeQuantityToDto(SizeQuantity sizeQuantity) {
        SizeQuantityResponseDto dto = new SizeQuantityResponseDto();
        dto.setSize(sizeQuantity.getSize());
        dto.setQuantity(sizeQuantity.getQuantity());
        dto.setColorId(sizeQuantity.getColor().getColorId());
        return dto;
    }


}
