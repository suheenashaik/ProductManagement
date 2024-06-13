package com.example.productManagement.products.entity;
import com.example.productManagement.products.util.Category;
import com.example.productManagement.products.util.ProductRelatedFor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "product_Table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQUENCE", allocationSize = 1)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private Category productCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_related_for")
    private ProductRelatedFor productRelatedFor;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_mrp")
    private Double productMRP;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "selling_price")
    private Long sellingPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Color> colors;


}