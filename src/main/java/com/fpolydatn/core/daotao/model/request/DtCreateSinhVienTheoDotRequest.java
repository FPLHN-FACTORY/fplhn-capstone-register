package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author thepvph20110
 */
@Getter
@Setter
public class DtCreateSinhVienTheoDotRequest {

    private String maSinhVien;

    @Length(max = EntityProperties.LENGTH_NAME)
    private String tenSinhVien;

    private String soDienThoai;

    private String email;

    private String khoa;

    private String chuyenNganh;

    private String monDATN;

}
