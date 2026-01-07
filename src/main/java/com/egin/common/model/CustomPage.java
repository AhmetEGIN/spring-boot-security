package com.egin.common.model;


import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPage<T> {

    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElementCount;
    private Integer totalPageCount;

    public static <C, X> CustomPage<C> of(final List<C> domain, final Page<X> page) {
        return CustomPage.<C>builder()
                .content(domain)
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElementCount(page.getTotalElements())
                .totalPageCount(page.getTotalPages())
                .build();
    }



}
