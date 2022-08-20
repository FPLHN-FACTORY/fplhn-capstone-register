package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public final class FindNhomDatnRequest extends PageableRequest {

    private String tenNhom;

    private String tenChuyenNganh;

    private String tenGvhd;

    private String trangThai;

}
