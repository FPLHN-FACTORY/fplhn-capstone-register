package com.fpolydatn.api.admin;

import com.fpolydatn.api.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public class FindHocKyRequest extends PageableRequest {

    private String tenHocKy;
}
