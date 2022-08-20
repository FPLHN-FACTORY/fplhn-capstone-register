package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author hungpv
 */
@Getter
@Setter
public class CreateMonDatnRequest extends BaseMonDatnRequest {
    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

    private String coSoId;

}
