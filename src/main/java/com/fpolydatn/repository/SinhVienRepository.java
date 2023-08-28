package com.fpolydatn.repository;

import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(SinhVienRepository.NAME)
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {

    public static final String NAME = "BaseSinhVienRepository";

    @Query(value = """
            SELECT sv.id,sv.ten_sinh_vien AS 'name'
            FROM sinh_vien sv INNER JOIN co_so cs 
            ON sv.co_so_id = cs.id
            AND sv.dot_dang_ky_id =:dotDangKyID
            AND sv.email=:email
            AND sv.co_so_id=:coSoId
            """, nativeQuery = true)
    SimpleEntityProj findSimpleEntityByTenSinhVien(@Param("coSoId") String coSoId, @Param("email") String email,
                                                   @Param("dotDangKyID") String dotDangKyID);

    @Query(value = """
            SELECT * FROM sinh_vien WHERE nhom_id IN 
            (SELECT a.id FROM nhom_datn a LEFT JOIN sinh_vien b ON a.id = b.nhom_id
                    WHERE a.created_date + :period < :currentTime
                    GROUP BY a.id HAVING COUNT(b.id) <= 3
            )
            """, nativeQuery = true)
    List<SinhVien> getListSinhVienByNhomId(@Param("currentTime") Long currentTime,
                                           @Param("period") Long period);

    @Query(value = """
            SELECT trang_thai
            FROM sinh_vien sv INNER JOIN co_so cs 
            ON sv.co_so_id = cs.id
            AND sv.dot_dang_ky_id =:dotDangKyID
            AND sv.id=:id
            AND sv.co_so_id=:coSoId
            """, nativeQuery = true)
    Integer getTrangThaiSinhVienForLogin(@Param("coSoId") String coSoId, @Param("id") String id,
                                                   @Param("dotDangKyID") String dotDangKyID);

}
