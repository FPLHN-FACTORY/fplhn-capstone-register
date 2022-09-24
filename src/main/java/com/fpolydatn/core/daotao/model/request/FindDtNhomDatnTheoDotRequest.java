package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindDtNhomDatnTheoDotRequest extends PageableRequest {

    private String tenDeTai;
    private String truongNhom;
    private String soLuong;
    private String giangVien;
    private String coSoId;

}
