package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtCreateNhomMonDatnRequest extends DtBaseNhomMonRequest {
    private String monDatnId;
    private String coSoId;
    private String maMon;
    private String tenMonDatn;
    private String chuyenNganhId;
}
