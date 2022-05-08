package com.mirea.optics_shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
@Getter
@Setter
public class OrderDetail {
    @Id
    @SequenceGenerator(name = "order_details_seq", sequenceName = "order_details_sequence", allocationSize = 1)
    @GeneratedValue(generator = "order_details_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;
}
