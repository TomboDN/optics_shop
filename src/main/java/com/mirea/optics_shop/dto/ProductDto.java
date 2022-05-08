package com.mirea.optics_shop.dto;

import com.mirea.optics_shop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    private String name;
    private String category;
    private double price;
    private String description;
    private String imagePath;

    public ProductDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imagePath = product.getImagePath();
    }
}
