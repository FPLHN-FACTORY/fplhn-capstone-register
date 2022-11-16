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
public class DtUpdateMonDatnRequest extends DtBaseMonDatnRequest {

    private String id;

    private String coSoId;

    private String chuyenNganhId;

}
