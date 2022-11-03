package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtUpdateNhomMonDatnRequest extends DtBaseNhomMonRequest {
    private String id;
    private String monDatnId;
    private String coSoId;
    private String chuyenNganhId;
}
