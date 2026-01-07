package com.egin.common.model;

import jakarta.validation.constraints.Min;
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
public class CustomPaging {

    @Min(value = 1, message = "Page number must be at least 1")
    private Integer pageNumber;

    @Min(value = 1, message = "Page size must be at least 1")
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber - 1;
    }


}
