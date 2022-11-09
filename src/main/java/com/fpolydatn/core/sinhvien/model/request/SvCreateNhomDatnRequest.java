package com.fpolydatn.core.sinhvien.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Data
public class SvCreateNhomDatnRequest {
    @NotBlank
    @NotEmpty
    private String giangVienId;

    @NotEmpty()
    @NotBlank()
    private String tenDeTai;

    @NotEmpty()
    @NotBlank()
    private String tenNhom;

    private Boolean matKhau;

    private String moTa;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    @NotEmpty()
    @NotBlank()
    private String coSoId;

    @NotBlank
    @NotBlank
    private String idMonDatn;
}
