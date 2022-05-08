package com.mirea.optics_shop.dto;

import com.mirea.optics_shop.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrderDto {
    private Long id;
    private Date creationDate;
    private int orderNumber;
    private double totalPrice;
    private Long userId;

    private List<OrderDetailDto> details;

    public OrderDto(Order order){
        this.id = order.getId();
        this.creationDate = order.getCreationDate();
        this.orderNumber = order.getOrderNumber();
        this.totalPrice = order.getTotalPrice();
        this.userId = order.getUserId();
    }
}
