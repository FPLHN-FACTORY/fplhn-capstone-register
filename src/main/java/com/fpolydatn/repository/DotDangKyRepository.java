package com.fpolydatn.repository;

import com.fpolydatn.core.daotao.model.response.KhoangThoiGianResponse;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(DotDangKyRepository.NAME)
public interface DotDangKyRepository extends JpaRepository<DotDangKy, String> {

    String NAME = "BaseDotDangKyRepository";

    @Query(value = """
            SELECT id, ten_dot_dang_ky AS 'name' FROM dot_dang_ky
            WHERE co_so_id = :coSoId order by created_date desc
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT id FROM dot_dang_ky ddk 
            WHERE :currentTime BETWEEN ddk.ngay_bat_dau AND ddk.ngay_ket_thuc
            AND co_so_id = :coSoId
            """, nativeQuery = true)
    String findDotDangKyID(@Param("coSoId") String coSoId, @Param("currentTime") Long currentTime);

    @Query(value = """
            SELECT ddk.ngay_bat_dau as ngay_bat_dau, ddk.han_sinh_vien as ngay_ket_thuc FROM dot_dang_ky ddk 
            WHERE ddk.id = :id
            """, nativeQuery = true)
    KhoangThoiGianResponse findMocThoiGianSinhVien(@Param("id") String id);

    @Query(value = """
            SELECT ddk.han_sinh_vien as ngay_bat_dau, ddk.han_giang_vien as ngay_ket_thuc FROM dot_dang_ky ddk 
            WHERE ddk.id = :id
            """, nativeQuery = true)
    KhoangThoiGianResponse findMocThoiGianGiangVien(@Param("id") String id);

    @Query(value = """
            SELECT ddk.han_sinh_vien as ngay_bat_dau, ddk.han_chu_nhiem_bo_mon as ngay_ket_thuc FROM dot_dang_ky ddk 
            WHERE ddk.id = :id
            """, nativeQuery = true)
    KhoangThoiGianResponse findMocThoiGianChuNhiem(@Param("id") String id);

}
