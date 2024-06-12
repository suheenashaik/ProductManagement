package com.example.productManagement.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "size_quantity")
public class SizeQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "size_quantity_seq")
    @SequenceGenerator(name = "size_quantity_seq", sequenceName = "SIZE_QUANTITY_SEQ", allocationSize = 1)
    @Column(name = "size_quantity_id", nullable = false)
    private Long sizeQuantityId;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}
