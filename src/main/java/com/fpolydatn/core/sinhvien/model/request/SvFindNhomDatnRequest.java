package com.fpolydatn.core.sinhvien.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SvFindNhomDatnRequest extends PageableRequest{

    private String tenDeTai;
    private String truongNhom;
    private String soLuong;
    private String giangVien;
    private String coSoId;
    private String dotDangKyId;

}
