package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindDtNhomDatnTheoDotRequest extends PageableRequest {

    private String maNhom;

    private String chuyenNganhId;

    private String dotDangKyId;

    private String trangThai;

    private String coSoId;

}
