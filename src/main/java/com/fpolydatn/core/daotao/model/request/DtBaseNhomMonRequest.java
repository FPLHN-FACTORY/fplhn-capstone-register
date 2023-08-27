package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DtBaseNhomMonRequest {
    @NotEmpty
    @NotBlank
    @NotNull
    private String maNhomMon;

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenNhomMon;

    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

    private String coSoId;

}
