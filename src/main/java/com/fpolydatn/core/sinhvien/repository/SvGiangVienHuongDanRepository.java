package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ToanNT
 */
@Repository
public interface SvGiangVienHuongDanRepository extends GiangVienHuongDanRepository {

    @Query(value = """
            SELECT g.id, g.ten_gvhd, g.ten_tai_khoan, g.so_nhom_huong_dan_toi_da FROM giang_vien_huong_dan g
            JOIN phan_cong_huong_dan p ON p.giang_vien_id = g.id 
            JOIN mon_datn m ON p.mon_datn_id = m.id 
            LEFT JOIN nhom_datn n ON n.giang_vien_id = g.id
            WHERE m.id = :idMon AND g.co_so_id = :coSoId AND g.dot_dang_ky_id = :dotDangKyId
            GROUP BY g.id
            HAVING COUNT(n.id) < g.so_nhom_huong_dan_toi_da
            """
            , nativeQuery = true)
    List<SvGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(@Param("idMon") String idMon,
                                                                      @Param("coSoId") String coSoId,
                                                                      @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT COUNT(nd.id) AS so_nhom_dang_huong_dan
                FROM giang_vien_huong_dan gvhd 
                LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE gvhd.id = :idGvhd 
                AND cs.id = :coSoId 
                AND ddk.id = :dotDangKyId
                GROUP BY gvhd.id
            """
            , nativeQuery = true)
    Integer countSoNhomGiangVienDangHuongDan(@Param("idGvhd") String idGvhd,
                                             @Param("coSoId") String coSoId,
                                             @Param("dotDangKyId") String dotDangKyId);
}
