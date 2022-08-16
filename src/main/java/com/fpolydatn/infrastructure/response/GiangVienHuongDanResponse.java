package com.fpolydatn.infrastructure.response;

/**
 * @author HangNT
 */
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.GiangVienHuongDan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author HangNT
 */
@Projection(types = {GiangVienHuongDan.class, CoSo.class})
public interface GiangVienHuongDanResponse {

    @Value("#{target.id}")
    UUID getId();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.email_fpt}")
    String getEmailFpt();

}