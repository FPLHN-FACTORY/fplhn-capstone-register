package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.PageableRequest;
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
