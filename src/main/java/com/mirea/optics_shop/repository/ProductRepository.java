package com.mirea.optics_shop.repository;

import com.mirea.optics_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    List<Product> findAllByCategory(String category);
}
