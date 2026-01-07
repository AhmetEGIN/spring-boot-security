package com.egin.common.model.dto.request;

import com.egin.common.model.CustomPaging;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CustomPagingRequest {

    private CustomPaging paging;

    public Pageable toPageable() {
        return PageRequest.of(
                Math.toIntExact(paging.getPageNumber()),
                Math.toIntExact(paging.getPageSize())
        );
    }

}
