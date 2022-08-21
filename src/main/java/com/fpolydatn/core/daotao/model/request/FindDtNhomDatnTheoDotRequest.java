package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindDtNhomDatnTheoDotRequest extends PageableRequest {

    private String tenNhom;
    private String chuyenNganhId;
    private String trangThai;
    private String tenGvhd;
    private String coSoId;

}
