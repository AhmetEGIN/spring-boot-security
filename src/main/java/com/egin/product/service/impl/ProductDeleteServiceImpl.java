package com.egin.product.service.impl;

import com.egin.product.exception.ProductNotFoundException;
import com.egin.product.model.entity.ProductEntity;
import com.egin.product.repository.ProductRepository;
import com.egin.product.service.ProductDeleteService;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private final ProductRepository productRepository;

    public ProductDeleteServiceImpl(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteProductById(String productId) {

        final ProductEntity productEntity = this.productRepository
                .findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(productEntity);

    }
}
