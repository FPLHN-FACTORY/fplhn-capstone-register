package com.fpolydatn.core.sinhvien.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SvAddSinhVienRequest {
    private String idNhom;
    private String idUser;
    private String inputPassword;
    private String coSoId;
    private String dotDangKyId;
}
