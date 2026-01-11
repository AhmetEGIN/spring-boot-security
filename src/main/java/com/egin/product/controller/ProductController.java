package com.egin.product.controller;


import com.egin.common.model.CustomPage;
import com.egin.common.model.dto.response.CustomPagingResponse;
import com.egin.common.model.dto.response.CustomResponse;
import com.egin.product.model.Product;
import com.egin.product.model.dto.request.ProductCreateRequest;
import com.egin.product.model.dto.request.ProductPagingRequest;
import com.egin.product.model.dto.request.ProductUpdateRequest;
import com.egin.product.model.dto.response.ProductResponse;
import com.egin.product.model.mapper.CustomPageToCustomPagingResponseMapper;
import com.egin.product.model.mapper.ProductToProductResponseMapper;
import com.egin.product.service.ProductCreateService;
import com.egin.product.service.ProductDeleteService;
import com.egin.product.service.ProductReadService;
import com.egin.product.service.ProductUpdateService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductCreateService productCreateService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;
    private final ProductReadService productReadService;

    public ProductController(
            ProductCreateService productCreateService,
            ProductUpdateService productUpdateService,
            ProductDeleteService productDeleteService,
            ProductReadService productReadService
    ) {
        this.productCreateService = productCreateService;
        this.productUpdateService = productUpdateService;
        this.productDeleteService = productDeleteService;
        this.productReadService = productReadService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<String> createProduct(
            @RequestBody @Valid final ProductCreateRequest request
    ) {

        final Product createdProduct = productCreateService
                .createProduct(request);

        return CustomResponse.successOf(createdProduct.getId());

    }

    @GetMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<ProductResponse> getProductById(
            @PathVariable(value = "product-id") @UUID final String productId
    ) {
        final Product product = productReadService.getProductById(productId);
        final ProductResponse productResponse = ProductToProductResponseMapper.toProductResponse(product);

        return CustomResponse.successOf(productResponse);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<ProductResponse>> getProducts(
            @RequestBody @Valid final ProductPagingRequest request
    ) {
        final CustomPage<Product> productPage = productReadService.getProducts(request);
        final CustomPagingResponse<ProductResponse> response = CustomPageToCustomPagingResponseMapper.toPagingResponse(productPage);

        return CustomResponse.successOf(response);
    }


    @PutMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<ProductResponse> updateProductById(
            @RequestBody @Valid final ProductUpdateRequest request,
            @PathVariable(value = "product-id") @UUID final String productId
    ) {
        final Product updatedProduct = productUpdateService.updateProductById(productId, request);
        final ProductResponse productResponse = ProductToProductResponseMapper.toProductResponse(updatedProduct);

        return CustomResponse.successOf(productResponse);
    }

    @DeleteMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteProductById(@PathVariable(value = "product-id") @UUID final String productId) {

        productDeleteService.deleteProductById(productId);
        return CustomResponse.SUCCESS;

    }

}
