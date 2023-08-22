package com.fpolydatn.core.chunhiem.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@ToString
@Setter
public class CnGvhdAndDanhSachNhomResponse {

    private String tenTaiKhoan;

    private List<String> listMaNhomDangHuongDan;
}
