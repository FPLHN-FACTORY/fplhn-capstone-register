package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author thangncph26123
 */
@Projection(types = {NhomDatn.class, SinhVien.class, ChuyenNganh.class, GiangVienHuongDan.class})
public interface NhomDatnResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.total_ma_sinh_vien}")
    String getTotal();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.trang_thai}")
    int getTrangThai();

}
