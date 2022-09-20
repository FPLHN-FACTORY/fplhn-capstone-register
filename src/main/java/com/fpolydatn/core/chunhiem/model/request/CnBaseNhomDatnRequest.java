package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author HangNT
 */
@Getter
@Setter
public abstract class CnBaseNhomDatnRequest {

    @NotEmpty()
    @NotBlank()
    private String maNhom;

    @NotEmpty()
    @NotBlank()
    @Column(length = EntityProperties.LENGTH_PASSWORD)
    private String matKhau;

    @NotEmpty()
    @NotBlank()
    private String maGiangVien;

    @NotEmpty()
    @NotBlank()
    private String maSinhVien;

    private String coSoId;

    private String dotDangKyId;
}
