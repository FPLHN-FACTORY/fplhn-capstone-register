package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.VaiTro;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
    private String soDienThoai;

    @NotEmpty
    @NotBlank
    @Length(min = 0, max = 50)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Length(min = 0, max = 50)
    private String emailFe;

    private String coSoId;

    private VaiTro vaiTro;

}