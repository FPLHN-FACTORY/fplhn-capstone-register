package com.fpolydatn.core.chunhiem.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnFindGvhdRequest {

    private String tenTaiKhoan;
    private String tenGvhd;
    private String monDatnId;
    private String idNhom;
    private String coSoId;
    private String dotDangKyId;

}
