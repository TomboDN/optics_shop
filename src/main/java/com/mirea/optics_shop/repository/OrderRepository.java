package com.mirea.optics_shop.repository;

import com.mirea.optics_shop.model.Order;
import com.mirea.optics_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByCreationDateDesc(Long userId);
}
