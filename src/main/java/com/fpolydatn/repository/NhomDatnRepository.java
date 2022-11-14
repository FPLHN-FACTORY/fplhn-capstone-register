package com.fpolydatn.repository;

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
                SELECT IF(COUNT(id) = 0, 0, MAX(SUBSTRING(ma_nhom, 4))) FROM nhom_datn 
                WHERE dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String genMaNhomTuDong(@Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT c.ma_co_so FROM  co_so c 
                WHERE  c.id = :coSoId
            """, nativeQuery = true)
    String getMaCoSo(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT a.id FROM nhom_datn a JOIN sinh_vien b ON a.id = b.nhom_id
            WHERE a.created_date + :period < :currentTime
            GROUP BY a.id HAVING COUNT(b.id) <= 3
            """, nativeQuery = true)
    List<String> listNhomKhongDuDieuKien(@Param("currentTime") Long currentTime,
                                         @Param("period") Long period);
}
