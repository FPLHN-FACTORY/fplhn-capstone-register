package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author hungpv
 */
@Getter
@Setter
public class DtBaseSinhVienTheoDotRequest {

    @NotEmpty
    @NotBlank
    @Length(max = 10)
    private String maSinhVien;

    @NotEmpty
    @NotBlank
    @Length(max = 100)
    private String tenSinhVien;

    @NotEmpty
    @NotBlank
    private String sdt;

    @NotEmpty
    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    @Length(max = 10)
    private String khoa;

    @NotEmpty
    @NotBlank
    private String monChuongTrinh;

    @NotEmpty
    @NotBlank
    private String chuyenNganhId;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    @NotEmpty
    @NotBlank
    private int trangThai;
}
