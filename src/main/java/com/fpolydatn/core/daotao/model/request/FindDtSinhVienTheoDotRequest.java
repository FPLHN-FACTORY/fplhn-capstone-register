package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thepvph20110
 */
@Setter
@Getter
@ToString
public class FindDtSinhVienTheoDotRequest extends PageableRequest {

    private String tenSinhVien = "";

    private String idChuyenNganh = "";

    private String idTrangThaiNhom = "";

    private String coSoId;

    private String idDotDangKy = "";

    private String sortNhomDatn = "maSinhVienAsc";

}
