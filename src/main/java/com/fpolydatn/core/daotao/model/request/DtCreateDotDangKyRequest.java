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
    private String hocKyId;

}
