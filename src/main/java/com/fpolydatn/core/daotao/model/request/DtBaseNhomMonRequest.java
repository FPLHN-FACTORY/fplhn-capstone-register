package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class DtBaseNhomMonRequest {
    @NotEmpty
    @NotBlank
    private String maMon;

    @NotEmpty
    @NotBlank
    private String tenMonDatn;

    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

    @NotEmpty
    @NotBlank
    private String coSoId;

    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

}
