package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindSinhVienRequest extends PageableRequest {
    private String id;
    private  String coSoId;
}
