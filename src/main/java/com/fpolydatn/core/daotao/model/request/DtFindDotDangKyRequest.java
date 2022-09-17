package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
public class DtFindDotDangKyRequest extends PageableRequest {

    private String coSoId;

}
