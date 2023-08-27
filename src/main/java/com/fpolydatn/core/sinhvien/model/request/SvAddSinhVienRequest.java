package com.fpolydatn.core.sinhvien.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public final class SvAddSinhVienRequest {

    @NotNull
    private String idNhom;

    private String idUser;

    @NotNull
    private String inputPassword;

    private String coSoId;

    private String dotDangKyId;

}
