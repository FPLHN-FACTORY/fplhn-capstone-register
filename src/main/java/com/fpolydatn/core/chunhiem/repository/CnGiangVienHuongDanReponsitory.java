package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnGiangVienHuongDanReponsitory extends GiangVienHuongDanRepository {
    @Query(value = """
            SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan
            """, nativeQuery = true)
    String getIdFromGiangVienId(@Param("tenTaiKhoan") String tenTaiKhoan);

    @Query(value = """
            SELECT CONCAT(ten_tai_khoan, '_' , ten_gvhd) AS thong_tin FROM giang_vien_huong_dan
            WHERE ((:#{#req.tenTaiKhoan} IS NULL OR :#{#req.tenTaiKhoan} LIKE ''
            OR ten_tai_khoan LIKE %:#{#req.tenTaiKhoan}%)
            OR (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
            OR ten_gvhd LIKE %:#{#req.tenGvhd}%))
            AND co_so_id = :#{#req.coSoId} AND dot_dang_ky_id = :#{#req.dotDangKyId}
            """, nativeQuery = true)
    List<String> getListGvhd(@Param("req") CnFindGvhdRequest req);

    @Query(value = """
            SELECT g.id, g.ten_tai_khoan, g.ten_gvhd, g.so_nhom_huong_dan_toi_da FROM giang_vien_huong_dan g
            JOIN phan_cong_huong_dan p ON p.giang_vien_id = g.id 
            JOIN mon_datn m ON p.mon_datn_id = m.id 
            LEFT JOIN nhom_datn n ON n.giang_vien_id = g.id
            WHERE m.id = :idMon AND g.co_so_id = :coSoId AND g.dot_dang_ky_id = :dotDangKyId
            GROUP BY g.id
            HAVING COUNT(n.id) < g.so_nhom_huong_dan_toi_da
            """
            , nativeQuery = true)
    List<CnGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(@Param("idMon") String idMon,
                                                                      @Param("coSoId") String coSoId,
                                                                      @Param("dotDangKyId") String dotDangKyId);

}
