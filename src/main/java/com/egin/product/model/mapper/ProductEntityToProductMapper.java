package com.egin.product.model.mapper;

import com.egin.product.model.Product;
import com.egin.product.model.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductEntityToProductMapper {

    public Product toProduct(final ProductEntity productEntity) {

        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .build();

    }

}
