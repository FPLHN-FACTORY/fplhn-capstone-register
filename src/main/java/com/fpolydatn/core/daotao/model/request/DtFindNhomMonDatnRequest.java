package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtFindNhomMonDatnRequest extends PageableRequest {

    private String coSoId;

}
