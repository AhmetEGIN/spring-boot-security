package com.egin.product.model.mapper;

import com.egin.product.model.Product;
import com.egin.product.model.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductToProductEntityMapper {

    public ProductEntity toProductEntity(final Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

}
