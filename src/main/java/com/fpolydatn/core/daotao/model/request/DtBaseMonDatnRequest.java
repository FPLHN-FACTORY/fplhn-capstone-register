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
public abstract class DtBaseMonDatnRequest {
    @NotEmpty
    @NotBlank
    private String maMon;

    @NotEmpty
    @NotBlank
    private String tenMonDatn;

}
