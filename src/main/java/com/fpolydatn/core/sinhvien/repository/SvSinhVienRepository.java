package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author thangdt
 */
public interface SvSinhVienRepository extends SinhVienRepository {

    @Query(value = "SELECT nhom_id FROM sinh_vien " +
            "WHERE co_so_id = :coSoId " +
            "AND dot_dang_ky_id = :dotDangKyId " +
            "AND id = :id", nativeQuery = true)
    String getNhomIdById(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId, @Param("id") String id);

    @Query(value = """
            SELECT COUNT(id) FROM sinh_vien
            WHERE nhom_id = :nhomId AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    int getSoLuongThanhVienById(@Param("nhomId") String nhomId,
                                @Param("coSoId") String coSoId,
                                @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT mon_chuong_trinh_id FROM sinh_vien 
            WHERE id = :id 
            AND co_so_id = :coSoId
            AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String getMonChuongTrinhIdById(@Param("id") String id,
                                   @Param("coSoId") String coSoId,
                                   @Param("dotDangKyId") String dotDangKyId);

}
