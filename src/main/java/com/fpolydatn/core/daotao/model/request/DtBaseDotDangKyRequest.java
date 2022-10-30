package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Getter
@Setter
public abstract class DtBaseDotDangKyRequest {

    @NotEmpty
    @NotBlank
    private String ngayBatDau;

    @NotEmpty
    @NotBlank
    private String ngayKetThuc;

    @NotEmpty
    @NotBlank
    private String hanSinhVien;

    @NotEmpty
    @NotBlank
    private String hanGiangVien;

    @NotEmpty
    @NotBlank
    private String hanChuNhiemBoMon;
}
