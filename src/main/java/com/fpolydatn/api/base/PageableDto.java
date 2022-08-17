package com.fpolydatn.api.base;

import com.fpolydatn.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class PageableDto {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int size = PaginationConstant.DEFAULT_SIZE;
}
