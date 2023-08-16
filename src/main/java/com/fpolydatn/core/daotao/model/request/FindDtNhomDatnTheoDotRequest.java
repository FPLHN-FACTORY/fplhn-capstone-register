package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FindDtNhomDatnTheoDotRequest extends PageableRequest {

    private String maNhom;

    private String dotDangKyId;

    private String chuyenNganhId;

    private String trangThai;

    private String coSoId;

}
