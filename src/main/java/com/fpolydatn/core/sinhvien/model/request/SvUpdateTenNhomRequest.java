package com.fpolydatn.core.sinhvien.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author SonPT
 */

@Getter
@Setter
public class SvUpdateTenNhomRequest {

    private String userId;

    private String nhomDatnId;

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenNhom;

}
