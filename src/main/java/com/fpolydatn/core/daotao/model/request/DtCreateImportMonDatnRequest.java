package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author huynq
 */
@Getter
@Setter
public class DtCreateImportMonDatnRequest extends DtBaseMonDatnRequest{

    private String chuyenNganhId;

    private String coSoId;

    private String nhomMonDatnId;

    private int stt;
}
