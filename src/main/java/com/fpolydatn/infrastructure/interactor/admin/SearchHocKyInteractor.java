package com.fpolydatn.infrastructure.interactor.admin;

import com.fpolydatn.infrastructure.interactor.PageableInteractor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */
@Getter
@Setter
public class SearchHocKyInteractor extends PageableInteractor {

    private String tenHocKy;
}
