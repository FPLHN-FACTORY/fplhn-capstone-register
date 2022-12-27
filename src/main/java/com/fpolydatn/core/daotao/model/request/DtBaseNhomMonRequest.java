package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

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
    private String tenNhomMon;

    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

    private String coSoId;

}
