package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huynq
 */
@Getter
@Setter
public final class DtFindPhanCongHuongDanRequest extends PageableRequest {

    private String tenGV;
    private String coSoId;
}
