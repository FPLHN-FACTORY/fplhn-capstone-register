package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hungpv
 */
@Getter
@Setter
public class DtFindMonDatnRequest extends PageableRequest {

    private String coSoId;

}
