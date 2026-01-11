package com.egin.product.model.mapper;

import com.egin.product.model.Product;
import com.egin.product.model.dto.response.ProductResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductToProductResponseMapper {

    public ProductResponse toProductResponse(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

}
