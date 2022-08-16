package com.fpolydatn.infrastructure.response;

import com.fpolydatn.model.domain.CanBo;
import com.fpolydatn.model.domain.CoSo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author HangNT
 */
@Projection(types = {CanBo.class, CoSo.class})
public interface CanBoResponse {

    @Value("#{target.id}")
    UUID getId();

    @Value("#{target.email_fpt}")
    String getEmailFPT();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

}