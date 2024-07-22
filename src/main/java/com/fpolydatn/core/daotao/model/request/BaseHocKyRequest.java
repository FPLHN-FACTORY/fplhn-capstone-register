package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author HangNT
 */
@Getter
@Setter
public abstract class BaseHocKyRequest {

    @NotEmpty()
    @NotBlank()
    @Length(min = 6, max = EntityProperties.LENGTH_NAME_SHORT)
    private String tenHocKy;

    @NotEmpty
    @NotBlank
    private String ngayBatDau;

    @NotEmpty
    @NotBlank
    private String ngayKetThuc;

}
