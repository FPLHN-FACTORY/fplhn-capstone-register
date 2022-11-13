package com.fpolydatn.core.daotao.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Vinhnv
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtGiangVienHDImportExcel {

    private String tenGvhd;

    private Short soNhomHuongDanToiDa;

    private String tenTaiKhoan;

    private String soDienThoai;

    private String emailFpt;

    private String emailFe;

    private String monHoc;

}
