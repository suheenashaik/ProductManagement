package com.example.productManagement.products.service;

import com.example.productManagement.products.Dtos.*;
import com.example.productManagement.products.entity.Color;
import com.example.productManagement.products.entity.Product;
import com.example.productManagement.products.entity.SizeQuantity;
import com.example.productManagement.products.repository.ColorRepository;
import com.example.productManagement.products.repository.ProductsRepository;
import com.example.productManagement.products.repository.SizeQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component
public class ProductsService extends ProductServiceImpl {

    @Autowired
    private ProductsRepository productRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeQuantityRepository sizeQuantityRepository;


    @Transactional
    public Product createProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductRelatedFor(dto.getProductRelatedFor());
        product.setProductCategory(dto.getProductCategory());
        product.setBrand(dto.getBrand());
        product.setProductMRP(dto.getProductMRP());
        product.setDiscount(dto.getDiscount());
        product.setSellingPrice(dto.getSellingPrice());

        List<Color> colors = new ArrayList<>();
        for (ColorRequestDto colorDto : dto.getColors()) {
            Color color = new Color();
            color.setColorName(colorDto.getColorName());
            List<SizeQuantity> sizeQuantities = new ArrayList<>();
            for (SizeQuantityRequestDto sizeQuantityDto : colorDto.getSizeQuantities()) {
                SizeQuantity sizeQuantity = new SizeQuantity();
                sizeQuantity.setSize(sizeQuantityDto.getSize());
                sizeQuantity.setQuantity(sizeQuantityDto.getQuantity());
                sizeQuantity.setColor(color);
                sizeQuantities.add(sizeQuantity);
            }
            color.setSizeQuantities(sizeQuantities);
            color.setProduct(product);
            colors.add(color);
        }
        product.setColors(colors);
        return productRepository.save(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            return emptyList();
        }
      return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public ProductResponseDto getProductById(long productId) {
        Optional<Product> product=this.productRepository.findById(productId);
        if(product.isPresent()){
            return convertToDto(product.get());
        }
        return null;
    }

    public List<ProductResponseDto> getProductsByName(String productName) {
        List<Product> products=this.productRepository.findByProductName(productName);
        if(!products.isEmpty()){
            return streamProducts(products);
        }
        return null;
    }

    public List<ProductResponseDto> getProductsByCategory(String productCategory) {
        List<Product> products=this.productRepository.findByProductCategory(productCategory);
        if(!products.isEmpty()){
            return streamProducts(products);
        }
        return null;
    }

    public List<ProductResponseDto> getProductsByRelatedFor(String productRelatedFor) {
        List<Product> products=this.productRepository.findProductsByRelatedFor(productRelatedFor);
        if(!products.isEmpty()){
            return streamProducts(products);
        }
        return null;
    }
    public List<ProductResponseDto> getProductsByBrand(String brand) {
        List<Product> products=this.productRepository.findProductsByBrand(brand);
        if(!products.isEmpty()){
            return streamProducts(products);
        }
        return null;
    }

}