package com.egin.product.model.mapper;

import com.egin.product.model.Product;
import com.egin.product.model.entity.ProductEntity;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ListProductEntityToListProductMapper {

    public List<Product> toListProduct(
            final List<ProductEntity> productEntityList
    ) {
        if (productEntityList == null || productEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        return productEntityList.stream()
                .map(ProductEntityToProductMapper::toProduct)
                .collect(Collectors.toList());
    }



}
