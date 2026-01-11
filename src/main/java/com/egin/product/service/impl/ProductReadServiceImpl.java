package com.egin.product.service.impl;

import com.egin.common.model.CustomPage;
import com.egin.product.exception.ProductNotFoundException;
import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductPagingRequest;
import com.egin.product.model.entity.ProductEntity;
import com.egin.product.model.mapper.ListProductEntityToListProductMapper;
import com.egin.product.model.mapper.ProductEntityToProductMapper;
import com.egin.product.repository.ProductRepository;
import com.egin.product.service.ProductReadService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductRepository productRepository;

    public ProductReadServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(String productId) {

        final ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return ProductEntityToProductMapper.toProduct(productEntity);
    }

    @Override
    public CustomPage<Product> getProducts(ProductPagingRequest request) {

        final Page<ProductEntity> productEntityPage = this.productRepository.findAll(request.toPageable());

        if (productEntityPage.getContent().isEmpty()) {
            throw new ProductNotFoundException();
        }
        final List<Product> products = ListProductEntityToListProductMapper
                .toListProduct(productEntityPage.getContent());


        return CustomPage.of(products, productEntityPage);
    }
}
