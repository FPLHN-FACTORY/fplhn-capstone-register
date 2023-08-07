package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@ToString
public class CnFindSinhVienTheoDotRequest extends PageableRequest {

    private String idDotDangKy;

    private String tenSinhVien;

    private String typeExcel;

    private String monChuongTrinh;

    private String monDatn;

    private String tenGvhd;

    private String maNhomSearch;

    private String trangThai;

    private String coSoId;

    private String idChuNhiem;

}
