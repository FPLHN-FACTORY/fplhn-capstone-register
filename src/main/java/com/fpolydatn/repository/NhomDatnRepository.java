package com.fpolydatn.repository;

import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(NhomDatnRepository.NAME)
public interface NhomDatnRepository extends JpaRepository<NhomDatn, String> {

    public static final String NAME = "BaseNhomDatnRepository";

    @Query(value = """
            SELECT ma_nhom
            FROM do_an_tot_nghiep.nhom_datn
            WHERE dot_dang_ky_id = :dotDangKyId AND co_so_id = :coSoId
            ORDER BY CAST(SUBSTRING_INDEX(ma_nhom, '_', -1) AS SIGNED) DESC
            LIMIT 1
            """, nativeQuery = true)
    String getNhomTaoGanNhat(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT a.id FROM nhom_datn a LEFT JOIN sinh_vien b ON a.id = b.nhom_id
            WHERE a.created_date + :period < :currentTime
            GROUP BY a.id HAVING COUNT(b.id) <= 3
            """, nativeQuery = true)
    List<String> listNhomKhongDuDieuKien(@Param("currentTime") Long currentTime,
                                         @Param("period") Long period);

    @Query(value = """
            SELECT COUNT(1) FROM nhom_datn nd 
                LEFT JOIN sinh_vien sv ON nd.id = sv.nhom_id
                WHERE nd.id = :nhomId
                GROUP BY nd.id 
            """, nativeQuery = true)
    Integer countSoLuongThanhVien(@Param("nhomId") String nhomId);

    @Query(value = """
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt,
            a.ma_sinh_vien, a.ten_sinh_vien, b.ma_nhom, b.ten_nhom, b.mo_ta, 
            IF(a.id = b.truong_nhom_id, 1, 0) AS chuc_vu 
            FROM sinh_vien a LEFT JOIN nhom_datn b ON a.nhom_id = b.id 
            WHERE a.nhom_id = :nhomDatnId AND a.co_so_id = :coSoId AND b.dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    List<SvShowSinhVienTheoNhomResponse> getDsSinhVienTrongNhom(@Param("nhomDatnId") String nhomDatnId);
}
