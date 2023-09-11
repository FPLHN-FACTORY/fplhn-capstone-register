package com.fpolydatn.core.chunhiem.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnDashboardGvhdResponse {

    private String gvhd;

    private String soNhomDangHuongDan;

    private List<CnDashboardNhomDatnResponse> listNhomDatn;
}
