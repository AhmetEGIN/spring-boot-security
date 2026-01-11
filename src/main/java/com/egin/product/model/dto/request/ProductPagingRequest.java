package com.egin.product.model.dto.request;

import com.egin.common.model.dto.request.CustomPagingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductPagingRequest extends CustomPagingRequest {
}
