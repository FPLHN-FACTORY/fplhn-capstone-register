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
    @Length(max = 10)
    private String khoa;
}
