package com.mirea.optics_shop.service;

import com.mirea.optics_shop.dto.ProductDto;
import com.mirea.optics_shop.model.Product;
import com.mirea.optics_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProductById(Long id){
        return productRepository.findProductById(id);
    }

    public ProductDto findProductDtoById(Long id){
        return new ProductDto(findProductById(id));
    }

    public List<Product> findAllProductsByCategory(String category){
        return productRepository.findAllByCategory(category);
    }

    public List<ProductDto> findAllProductsByCategoryConvertProductDto(String category){
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> allProductsByCategory = findAllProductsByCategory(category);
        for (Product product : allProductsByCategory){
            productDtoList.add(new ProductDto(product));
        }
        return productDtoList;
    }

}
