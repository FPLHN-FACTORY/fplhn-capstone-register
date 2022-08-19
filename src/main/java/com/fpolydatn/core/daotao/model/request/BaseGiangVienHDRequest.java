package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * @author Vinhnv
 */
@Getter
@Setter
public abstract class BaseGiangVienHDRequest {

    @NotEmpty
    @NotBlank
    private String tenGvhd;

    @NotEmpty
    @NotBlank
    private Short soNhomHuongDanToiDa;

    @NotEmpty
    @NotBlank
    private String tenTaiKhoan;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER)
    private String soDienThoai;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_EMAIL_FPT)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_EMAIL_FE)
    private String emailFe;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    private String coSoId;
}
