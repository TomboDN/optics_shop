package com.mirea.optics_shop.dto;

import com.mirea.optics_shop.model.OrderDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDto {
    private Long id;

    private Long productId;
    private String productName;

    private int quantity;
    private double price;
    private double totalPrice;

    public OrderDetailDto(OrderDetail orderDetail){
        this.id = orderDetail.getId();
        this.productId = orderDetail.getProduct().getId();
        this.productName = orderDetail.getProduct().getName();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
        this.totalPrice = orderDetail.getTotalPrice();
    }
}
