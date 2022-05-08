package com.mirea.optics_shop.model;

import com.mirea.optics_shop.dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "products_seq", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(generator = "products_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "image_path")
    private String imagePath;

    public Product(ProductDto productDto){
        this.setId(productDto.getId());
        this.setName(productDto.getName());
        this.setCategory(productDto.getCategory());
        this.setPrice(productDto.getPrice());
        this.setDescription(productDto.getDescription());
        this.setImagePath(productDto.getImagePath());
    }
}
