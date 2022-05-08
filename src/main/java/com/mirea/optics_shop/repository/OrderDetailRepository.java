package com.mirea.optics_shop.repository;

import com.mirea.optics_shop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrder_Id(Long id);
}
