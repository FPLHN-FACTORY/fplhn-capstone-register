package com.fpolydatn.repository;

import com.fpolydatn.model.domain.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, UUID> {

    @Query(value = "SELECT id, created_date, last_modified_date, co_so_id, chuyen_nganh_id, dot_dang_ky_id, email, " +
            "  khoa, ma_sinh_vien, mon_datn_id, nhom_id, so_dien_thoai, ten_sinh_vien, trang_thai FROM sinh_vien sv " +
            "   WHERE sv.email =:emailFPT " +
            "  AND sv.co_so_id = :coSoId", nativeQuery = true)
    SinhVien findSinhVienByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
