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

    private String tenChuyenNganh; //

    private Integer tongSLSinhVien; //

    private Integer tongSoNhom; //

    private Integer tongSoGvhd; //

    private Integer tongSoGvhdDangCoNhom; //

    private Integer tongSoNhomDaChotDeTai; //

    private Integer tongSinhVienDaCoNhomHopLe; //

    private Integer tongSLSinhVienDuDieuKien; //

    private Integer tongSoGvhdChuaCoNhomNao; //

    private Integer tongSoNhomChuaChotDeTai; //

    private Integer tongSinhVienChuaCoNhomHopLe; //

    private Integer tongSinhVienChuaCoNhom; //

    private Integer tongSLNhomChuaCoGvhd;

    private Integer tongSLSinhVienKhongDuDieuKien; //

    private List<CnDashboardGvhdResponse> listGvhd;
}
