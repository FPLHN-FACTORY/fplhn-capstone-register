package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SinhVien.class})
public interface DtSinhVienResponse {

    String getId();

    @Value("#{target.stt}")
    int getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.chuc_vu}")
    Integer getChucVu();

}
