package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author thepvph20110
 */
@Setter
@Getter
@ToString
public class FindDtSinhVienTheoDotRequest extends PageableRequest {

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenSinhVien = "";

    private String idChuyenNganh = "";

    private String idTrangThaiNhom = "";

    private String coSoId;

    private String idDotDangKy = "";

    private String sortNhomDatn = "maSinhVienAsc";

}
