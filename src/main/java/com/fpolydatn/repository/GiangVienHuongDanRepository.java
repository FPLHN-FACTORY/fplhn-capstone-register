package com.fpolydatn.repository;

import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(GiangVienHuongDanRepository.NAME)
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDan, String> {

    public static final String NAME = "BaseGiangVienHuongDanRepository";

    @Query(value = """
            SELECT gvhd.id, gvhd.ten_gvhd AS 'name'
             FROM giang_vien_huong_dan gvhd INNER JOIN co_so cs 
             ON gvhd .co_so_id = cs.id
             AND gvhd .dot_dang_ky_id =:dotDangKyID
             AND gvhd.co_so_id=:coSoId
             AND gvhd.email_fpt=:emailFPT
             """, nativeQuery = true)
    SimpleEntityProj findSimpleEntityByTenGiangVienHuongDan(@Param("coSoId") String coSoId, @Param("emailFPT") String emailFPT,
                                                            @Param("dotDangKyID") String dotDangKyID);

    @Query(value = """
            SELECT IF(COUNT(id) IS NULL, 0, COUNT(id)) FROM nhom_datn
            WHERE giang_vien_id = :idGiangVien 
            AND dot_dang_ky_id = :dotDangKyID
            """, nativeQuery = true)
    int getSoNhomGiangVienDangHuongDan(@Param("idGiangVien") String id, @Param("dotDangKyID") String dotDangKyID);
}
