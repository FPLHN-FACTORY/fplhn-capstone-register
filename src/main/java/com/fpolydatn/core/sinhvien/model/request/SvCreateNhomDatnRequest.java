package com.fpolydatn.core.sinhvien.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Data
public class SvCreateNhomDatnRequest {
    @NotBlank
    @NotEmpty
    private String giangVienId;

    @NotEmpty()
    @NotBlank()
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenDeTai;

    @NotEmpty()
    @NotBlank()
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenNhom;

    private Boolean matKhau;

    @Length(max = 255)
    private String moTa;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    @NotEmpty()
    @NotBlank()
    private String coSoId;

    @NotBlank
    @NotBlank
    private String idMonDatn;
}
