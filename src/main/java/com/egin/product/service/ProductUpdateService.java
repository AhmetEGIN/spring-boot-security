package com.egin.product.service;

import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductUpdateRequest;

public interface ProductUpdateService {
    Product updateProductById(final String productId, final ProductUpdateRequest request);
}
