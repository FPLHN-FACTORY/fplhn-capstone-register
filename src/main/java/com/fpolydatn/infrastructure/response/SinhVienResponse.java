package com.fpolydatn.infrastructure.response;

import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author HangNT
 */
@Projection(types = {SinhVien.class, CoSo.class})
public interface SinhVienResponse {

    @Value("#{target.id}")
    UUID getId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

}