package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CnFindSinhVienTheoDotRequest extends PageableRequest {
    private String idDotDangKy;
    private String tenSinhVien;
    private String coSoId;
    private String idChuNhiem;
}
