package com.fpolydatn.core.admin.service.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hoangnt
 */
@Getter
@Setter
public class DtFindChuyenNganhRequest extends PageableRequest {

    private String coSoId;
    private String tenChuyenNganh;
}
