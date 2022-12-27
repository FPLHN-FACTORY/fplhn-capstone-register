package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 10)
    private String maMon;

    @NotEmpty
    @NotBlank
    @Length(max = 100)
    private String tenMonDatn;

    private String nhomMonDatnId;

}
