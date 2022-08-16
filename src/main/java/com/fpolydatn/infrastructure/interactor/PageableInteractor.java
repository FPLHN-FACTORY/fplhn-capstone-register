package com.fpolydatn.infrastructure.interactor;

import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class PageableInteractor {

    private int limit;
    private int offset;
}
