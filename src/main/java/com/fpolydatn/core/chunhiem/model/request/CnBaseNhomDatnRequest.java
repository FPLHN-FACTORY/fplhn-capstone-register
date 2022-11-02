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

    private String monDatnId;

    @NotEmpty()
    @NotBlank()
    @Column(length = EntityProperties.LENGTH_PASSWORD)
    private String matKhau;

    @NotEmpty()
    @NotBlank()
    private String idGiangVien;

    @NotEmpty()
    @NotBlank()
    private String maSinhVien;

    private String coSoId;

    private String dotDangKyId;
}
