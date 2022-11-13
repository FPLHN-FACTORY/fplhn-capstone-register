package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.VaiTro;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class DtBaseCanBoRequest {

    @NotEmpty
    @NotBlank
    private String tenCanBo;

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
    @Length(min = 0, max = 30)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_EMAIL_FE)
    @Length(min = 0, max = 30)
    private String emailFe;

    private String coSoId;

    private VaiTro vaiTro;

}