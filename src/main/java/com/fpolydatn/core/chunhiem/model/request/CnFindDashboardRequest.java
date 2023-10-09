package com.fpolydatn.core.chunhiem.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnFindDashboardRequest {

    private String dotDangKyId;

    private String chuyenNganhId;

    private String coSoId;

    private String idChuNhiem;

    private String maGvhd;

    private String monDatnId;

    private Integer soNhomHuongDanToiDa;

    private String tenDeTaiSearch = "";

    private String sort = "sortDesc";
}
