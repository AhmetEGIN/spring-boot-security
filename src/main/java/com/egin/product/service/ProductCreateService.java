package com.egin.product.service;

import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductCreateRequest;

public interface ProductCreateService {
    Product createProduct(final ProductCreateRequest request);
}
