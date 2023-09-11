package com.fpolydatn.core.chunhiem.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class CnDashboardResponse {

    private String tenChuyenNganh;

    private Integer tongSLSinhVien;

    private Integer tongSoNhom;

    private Integer tongSoNhomDaChotDeTai;

    private Integer tongSoNhomChuaChotDeTai;

    private Integer tongSoGvhd;

    private Integer tongSoGvhdDangCoNhom;

    private Integer tongSoGvhdChuaCoNhomNao;

    private Integer tongSinhVienDaCoNhomHopLe;

    private Integer tongSinhVienChuaCoNhomHopLe;

    private Integer tongSinhVienChuaCoNhom;

    private List<CnDashboardGvhdResponse> listGvhd;
}
