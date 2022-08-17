package com.fpolydatn.infrastructure.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class PageableQuery {

    private int limit;
    private int offset;
}
