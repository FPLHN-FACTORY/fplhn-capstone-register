package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseChuyenNganhRequest {

    private String coSoId;

    @NotEmpty
    @NotBlank
    private String chuNhiemBoMon;

    @NotEmpty
    @NotBlank
    private String tenChuyenNganh;
}
