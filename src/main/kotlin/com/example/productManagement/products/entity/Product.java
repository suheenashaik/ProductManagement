package com.example.productManagement.products.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQUENCE", allocationSize = 1)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_related_for")
    private String productRelatedFor;

    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_mrp")
    private Double productMRP;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "selling_price")
    private Long sellingPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Color> color;
}