package com.fpolydatn.core.giangvien.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangdt
 */

@Getter
@Setter
public final class GvFindNhomDatnRequest extends PageableRequest{

    private String GiangVienHDId;
    private String hocKyId;
    private String chuyenNganhId;
    private String dotDangKyId;

}
