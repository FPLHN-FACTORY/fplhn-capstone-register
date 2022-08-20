package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thepvph20110
 */
@Setter
@Getter
public class FindDtSinhVienRequest extends PageableRequest {

    private String getTenSinhVien;
    private String getTenChuyenNganh;
    private String getTrangThaiNhom;
    private String getTenDotDangKy;
    private String getCoSoId;

}
