package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class DtCreateNhomMonDatnRequest extends DtBaseNhomMonRequest {

    @NotEmpty
    @NotBlank
    private String coSoId;

}
