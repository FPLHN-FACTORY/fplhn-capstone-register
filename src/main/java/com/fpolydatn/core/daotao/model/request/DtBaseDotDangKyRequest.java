package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Getter
@Setter
public abstract class DtBaseDotDangKyRequest {

    @NotEmpty
    @NotBlank
    private String tenDotDangKy;

    @NotEmpty
    @NotBlank
    private Long ngayBatDau;

    @NotEmpty
    @NotBlank
    private Long ngayKetThuc;
}
