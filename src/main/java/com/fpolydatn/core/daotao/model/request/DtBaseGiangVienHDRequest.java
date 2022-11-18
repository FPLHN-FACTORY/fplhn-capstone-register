package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author Vinhnv
 */
@Getter
@Setter
public abstract class DtBaseGiangVienHDRequest {

    @NotEmpty
    @NotBlank
    private String tenGvhd;

    @Min(value = 1)
    @Max(value = 7)
    private Short soNhomHuongDanToiDa;

    @NotEmpty
    @NotBlank
    private String tenTaiKhoan;

    @Length(max = 20)
    private String soDienThoai;

    @NotEmpty
    @NotBlank
    @Length(max = 50)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Length(max = 50)
    private String emailFe;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    private String coSoId;
}
