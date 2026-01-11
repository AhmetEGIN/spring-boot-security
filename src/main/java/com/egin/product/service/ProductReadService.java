package com.egin.product.service;

import com.egin.common.model.CustomPage;
import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductPagingRequest;

public interface ProductReadService {
    Product getProductById(final String productId);

    CustomPage<Product> getProducts(final ProductPagingRequest request);
}
