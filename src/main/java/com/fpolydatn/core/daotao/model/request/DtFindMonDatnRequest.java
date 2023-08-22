package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hungpv
 */
@Getter
@Setter
@ToString
public class DtFindMonDatnRequest extends PageableRequest {

    private String coSoId;

    private String dotDangKyId;

    private String chuyenNganhId;

    private String maMon;

}
