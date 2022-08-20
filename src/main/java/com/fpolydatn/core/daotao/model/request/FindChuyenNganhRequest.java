package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hoangnt
 */
@Getter
@Setter
public class FindChuyenNganhRequest extends PageableRequest {

    private String coSoId;
    private String tenChuyenNganh;
}
