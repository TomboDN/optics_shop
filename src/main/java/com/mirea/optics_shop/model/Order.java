package com.mirea.optics_shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(generator = "orders_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "user_id")
    private Long userId;
}
