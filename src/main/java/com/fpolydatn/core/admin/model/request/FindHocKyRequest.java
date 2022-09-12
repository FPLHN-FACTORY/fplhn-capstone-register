package com.fpolydatn.core.admin.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public final class FindHocKyRequest extends PageableRequest {

    private String tenHocKy;
}
