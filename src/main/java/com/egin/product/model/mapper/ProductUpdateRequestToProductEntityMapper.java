package com.egin.product.model.mapper;

import com.egin.product.model.dto.request.ProductUpdateRequest;
import com.egin.product.model.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductUpdateRequestToProductEntityMapper {

    public ProductEntity toProductEntity(ProductEntity productEntity, final ProductUpdateRequest productUpdateRequest) {

        if (productEntity == null) {
            return null;
        }

        if (productUpdateRequest == null) {
            return productEntity;
        }

        if (productUpdateRequest.getName() != null) {
            productEntity.setName(productUpdateRequest.getName());
        }

        if (productUpdateRequest.getPrice() != null) {
            productEntity.setPrice(productUpdateRequest.getPrice());
        }
        return productEntity;
    }


}
