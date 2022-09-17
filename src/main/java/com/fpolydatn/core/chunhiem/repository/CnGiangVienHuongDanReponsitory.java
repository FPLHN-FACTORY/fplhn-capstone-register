package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CnGiangVienHuongDanReponsitory extends GiangVienHuongDanRepository {
 @Query(value = """
         SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan
         """,nativeQuery = true)
    String getIdFromGiangVienId(@Param("tenTaiKhoan") String tenTaiKhoan);

}
