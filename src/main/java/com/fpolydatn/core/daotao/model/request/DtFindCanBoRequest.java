package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author caodinh
 */
@Getter
@Setter
public class DtFindCanBoRequest extends PageableRequest {

    private String coSoID;

}
