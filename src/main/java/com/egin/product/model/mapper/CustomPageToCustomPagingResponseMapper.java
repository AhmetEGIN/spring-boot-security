package com.egin.product.model.mapper;

import com.egin.common.model.CustomPage;
import com.egin.common.model.dto.response.CustomPagingResponse;
import com.egin.product.model.Product;
import com.egin.product.model.dto.response.ProductResponse;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CustomPageToCustomPagingResponseMapper {

    public static CustomPagingResponse<ProductResponse> toPagingResponse(
            final CustomPage<Product> productPage
            ) {

        if (productPage == null) {
            return null;
        }

        return CustomPagingResponse.<ProductResponse>builder()
                .content(toProductResponseList(productPage.getContent()))
                .totalElementCount(productPage.getTotalElementCount())
                .totalPageCount(productPage.getTotalPageCount())
                .pageNumber(productPage.getPageNumber())
                .pageSize(productPage.getPageSize())
                .build();
    }

    private static List<ProductResponse> toProductResponseList(List<Product> content) {

        if (content == null) {
            return Collections.emptyList();
        }

        return content.stream()
                .map(ProductToProductResponseMapper::toProductResponse)
                .collect(Collectors.toList());
    }


}
