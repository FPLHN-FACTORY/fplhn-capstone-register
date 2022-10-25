package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Getter
@Setter
public class DtCreateDotDangKyRequest extends DtBaseDotDangKyRequest {

    @NotEmpty
    @NotBlank
    @Length(min = 6, max = 15)
    private String tenDotDangKy;

    @NotEmpty
    @NotBlank
    private String hocKyId;

    private String coSoId;
}
