package com.egin.product.service.impl;

import com.egin.product.exception.ProductNotFoundException;
import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductUpdateRequest;
import com.egin.product.model.entity.ProductEntity;
import com.egin.product.model.mapper.ProductEntityToProductMapper;
import com.egin.product.model.mapper.ProductUpdateRequestToProductEntityMapper;
import com.egin.product.repository.ProductRepository;
import com.egin.product.service.ProductUpdateService;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductRepository productRepository;

    public ProductUpdateServiceImpl(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Override
    public Product updateProductById(
            String productId,
            ProductUpdateRequest productUpdateRequest
    ) {

            final ProductEntity productEntity = this.productRepository.findById(productId)
                    .orElseThrow(ProductNotFoundException::new);

        ProductUpdateRequestToProductEntityMapper.toProductEntity(productEntity, productUpdateRequest);

        ProductEntity updatedProductEntity = productRepository.save(productEntity);

        return ProductEntityToProductMapper.toProduct(updatedProductEntity);
    }
}
