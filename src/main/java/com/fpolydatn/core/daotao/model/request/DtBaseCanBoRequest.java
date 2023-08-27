package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.VaiTro;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class DtBaseCanBoRequest {

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenCanBo;

    @NotEmpty
    @NotBlank
    private String tenTaiKhoan;

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

    private String coSoId;

    @NotNull
    private VaiTro vaiTro;

}