package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author hoangnt
 */
@Getter
@Setter
public abstract class DtBaseChuyenNganhRequest {

    private String coSoId;

    private String chuNhiemBoMon;

    @NotEmpty
    @NotBlank
    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenChuyenNganh;

}
