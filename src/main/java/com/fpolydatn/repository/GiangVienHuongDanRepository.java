package com.fpolydatn.repository;

import com.fpolydatn.entity.GiangVienHuongDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(GiangVienHuongDanRepository.NAME)
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDan, String> {

    String NAME = "BaseGiangVienHuongDanRepository";

    @Query(value = """
        SELECT IF(COUNT(id) IS NULL, 0, COUNT(id)) FROM nhom_datn
        WHERE giang_vien_id = :idGiangVien 
        AND dot_dang_ky_id = 'e3afc2f8-3562-4606-9aae-62366445d077'
        """, nativeQuery = true)
    int getSoNhomGiangVienDangHuongDan(@Param("idGiangVien") String id);
}
