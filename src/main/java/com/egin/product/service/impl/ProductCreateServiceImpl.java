package com.egin.product.service.impl;

import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductCreateRequest;
import com.egin.product.model.entity.ProductEntity;
import com.egin.product.model.mapper.ProductCreateRequestToProductEntityMapper;
import com.egin.product.model.mapper.ProductEntityToProductMapper;
import com.egin.product.repository.ProductRepository;
import com.egin.product.service.ProductCreateService;
import org.springframework.stereotype.Service;

@Service
public class ProductCreateServiceImpl implements ProductCreateService {


    private final ProductRepository productRepository;

    public ProductCreateServiceImpl(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductCreateRequest request) {

        final ProductEntity productEntity = ProductCreateRequestToProductEntityMapper.toProductEntity(request);
        ProductEntity savedProductEntity = productRepository.save(productEntity);


        return ProductEntityToProductMapper.toProduct(savedProductEntity);
    }
}
