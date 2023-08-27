package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Vinhnv
 */
@Getter
@Setter
public abstract class DtBaseGiangVienHDRequest {

    @NotEmpty
    @NotBlank
    @Length(max = 100)
    private String tenGvhd;

    @Min(value = 1)
    @Max(value = 7)
    private Short soNhomHuongDanToiDa;

    @NotEmpty
    @NotBlank
    @Length(max = 20)
    private String tenTaiKhoan;

    @Length(max = 20)
    @NotNull
    private String soDienThoai;

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_EMAIL)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_EMAIL)
    private String emailFe;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    private String coSoId;
}
