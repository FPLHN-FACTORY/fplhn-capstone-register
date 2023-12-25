package com.fpolydatn.core.sinhvien.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author SonPT
 */
@Getter
@Setter
public class SvCreateNhomDatnRequest {

    private String giangVienId;

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenDeTai;

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenNhom;

    @NotNull
    private Boolean laNhomBiMat;

    @Length(max = 255)
    private String moTa;

    @NotEmpty
    @NotBlank
    private String dotDangKyId;

    @NotEmpty
    @NotBlank
    private String coSoId;

    @NotBlank
    @NotEmpty
    private String idMonDatn;

    private String nguoiTaoId;

}
