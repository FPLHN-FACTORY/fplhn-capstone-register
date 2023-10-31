package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author hungpv
 */
@Getter
@Setter
public class DtBaseSinhVienTheoDotRequest {

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_CODE)
    private String maSinhVien;

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenSinhVien;

    private String sdt;

    @NotEmpty
    @NotBlank
    private String email;

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

    @NotNull
    private Integer trangThai;
}
