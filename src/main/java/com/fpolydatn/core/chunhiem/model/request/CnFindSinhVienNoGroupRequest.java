package com.fpolydatn.core.chunhiem.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnFindSinhVienNoGroupRequest {

    private String maSinhVien;
    private String tenSinhVien;
    private String coSoId;
    private String dotDangKyId;
}
