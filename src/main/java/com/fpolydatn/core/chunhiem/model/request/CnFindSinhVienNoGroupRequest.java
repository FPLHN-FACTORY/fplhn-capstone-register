package com.fpolydatn.core.chunhiem.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnFindSinhVienNoGroupRequest {

    private String idNhom;
    private String maSinhVien;
    private String tenSinhVien;
    private String nhomMonDatnId;
    private String coSoId;
    private String dotDangKyId;
}
