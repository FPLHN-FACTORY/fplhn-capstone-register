package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thepvph20110
 */
@Setter
@Getter
public class FindDtSinhVienTheoDotRequest extends PageableRequest {

    private String tenSinhVien="";

    private String idChuyenNganh="";

    private String idTrangThaiNhom="";

    private String idDotDangKy="";

    private String coSoId;

}
