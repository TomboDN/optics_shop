package com.mirea.optics_shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CartLineDto implements Serializable {
    private ProductDto productDto;
    private int quantity;

    public double getTotalPrice() {
        return this.productDto.getPrice() * this.quantity;
    }
}
