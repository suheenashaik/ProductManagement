package com.example.productManagement.products.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.productManagement.products.util.ColorNames;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_seq")
    @SequenceGenerator(name = "color_seq", sequenceName = "COLOR_SEQ", allocationSize = 1)
    @Column(name = "color_id", nullable = false)
    private Long colorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_name", nullable = false)
    private ColorNames colorName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SizeQuantity> sizeQuantities = new ArrayList<>();


}