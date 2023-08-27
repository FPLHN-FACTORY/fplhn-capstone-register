package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author hoangnt
 */
@Getter
@Setter
public class DtFindChuyenNganhRequest extends PageableRequest {

    private String coSoId;

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenChuyenNganh;

}
