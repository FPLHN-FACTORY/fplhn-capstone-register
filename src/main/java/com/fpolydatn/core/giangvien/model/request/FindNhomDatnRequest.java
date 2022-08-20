package com.fpolydatn.core.giangvien.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangdt
 */

@Getter
@Setter
public final class FindNhomDatnRequest extends PageableRequest{

    private String tenHocKy;
    private String tenChuyenNganh;
    private String tenDotDangKy;

}
