package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.infrastructure.constant.VaiTro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtCanBoExcelImport {

    private String tenCanBo;

    private String tenTaiKhoan;

    private String emailFPT;

    private String emailFE;

    private String soDienThoai;

    private String vaiTro;

    private String coSoId;
}
