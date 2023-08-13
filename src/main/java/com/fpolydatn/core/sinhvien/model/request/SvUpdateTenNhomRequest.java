package com.fpolydatn.core.sinhvien.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Getter
@Setter
public class SvUpdateTenNhomRequest {

    private String coSoId;

    private String dotDangKyId;

    private String userId;

    @Length(max = 255)
    private String tenNhom;

}
