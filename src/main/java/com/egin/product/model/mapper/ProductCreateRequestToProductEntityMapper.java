package com.egin.product.model.mapper;

import com.egin.product.model.dto.request.ProductCreateRequest;
import com.egin.product.model.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductCreateRequestToProductEntityMapper {

    public ProductEntity toProductEntity(final ProductCreateRequest productCreateRequest) {
        return ProductEntity.builder()
                .name(productCreateRequest.getName())
                .price(productCreateRequest.getPrice())
                .build();
    }


}
