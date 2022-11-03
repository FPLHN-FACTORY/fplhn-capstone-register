package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CnFindSinhVienTheoNhomRequest extends PageableRequest {
    private String idNhom;
    private String coSoId;
    private String dotDangKyId;
}
