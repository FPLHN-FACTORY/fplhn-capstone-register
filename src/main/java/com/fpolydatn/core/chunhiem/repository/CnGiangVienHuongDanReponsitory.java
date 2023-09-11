package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanDanhSachNhomResponse;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.chunhiem.model.response.CnGvhdSearchResponse;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnGiangVienHuongDanReponsitory extends GiangVienHuongDanRepository {
    @Query(value = """
            SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String getIdFromGiangVienId(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT CONCAT(g.ten_tai_khoan, '_' , g.ten_gvhd, ' (',
             (SELECT COUNT(i.id) FROM nhom_datn i WHERE i.giang_vien_id = g.id)
             , '/' , g.so_nhom_huong_dan_toi_da, ')' ) 
             AS thong_tin , g.so_nhom_huong_dan_toi_da FROM giang_vien_huong_dan g
            JOIN phan_cong_huong_dan p ON p.giang_vien_id = g.id 
            JOIN mon_datn m ON p.mon_datn_id = m.id 
            LEFT JOIN nhom_datn n ON n.giang_vien_id = g.id
            WHERE ((:#{#req.tenTaiKhoan} IS NULL OR :#{#req.tenTaiKhoan} LIKE ''
            OR g.ten_tai_khoan LIKE %:#{#req.tenTaiKhoan}%)
            OR (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
            OR g.ten_gvhd LIKE %:#{#req.tenGvhd}%))
            AND g.co_so_id = :#{#req.coSoId} AND g.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (SELECT pc.giang_vien_id FROM phan_cong_huong_dan pc WHERE pc.giang_vien_id = g.id AND pc.mon_datn_id = :#{#req.monDatnId}) IS NOT NULL
            GROUP BY g.id
            HAVING COUNT(n.id) < g.so_nhom_huong_dan_toi_da
            """, nativeQuery = true)
    List<CnGvhdSearchResponse> getListGvhd(@Param("req") CnFindGvhdRequest req);


    @Query(value = "select giang_vien_id from phan_cong_huong_dan JOIN giang_vien_huong_dan on giang_vien_huong_dan.id = phan_cong_huong_dan.giang_vien_id where giang_vien_id = :idGiangVien" +
            " and mon_datn_id = :idMonDatn AND giang_vien_huong_dan.dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String checkIdGiangVienAndMonDatn(@Param("idGiangVien") String idGiangVien, @Param("idMonDatn") String idMonDatn, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT g.id, g.ten_tai_khoan, g.ten_gvhd, (SELECT COUNT(i.id) FROM nhom_datn i
             WHERE i.giang_vien_id = g.id) as so_nhom_dang_huong_dan
            , g.so_nhom_huong_dan_toi_da FROM giang_vien_huong_dan g
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

    @Query(value = """
            SELECT distinct * FROM giang_vien_huong_dan a 
            LEFT JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            LEFT JOIN mon_datn c ON b.mon_datn_id = c.id
            LEFT JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            LEFT JOIN can_bo e ON d.chu_nhiem_bo_mon = e.id
            WHERE a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId 
            AND e.id = :idChuNhiem
            """, nativeQuery = true)
    List<GiangVienHuongDan> getAllGiangVienByIdCoSoAndByIdDotDangKy(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            WITH DanhSachNhom AS (
            SELECT b.giang_vien_id,
            GROUP_CONCAT(b.ma_nhom ORDER BY b.id SEPARATOR ', ') AS danh_sach_ma_nhom
            FROM nhom_datn b GROUP BY b.giang_vien_id)
            SELECT a.ten_tai_khoan, COALESCE(dsn.danh_sach_ma_nhom, '') AS danh_sach_nhom
            FROM giang_vien_huong_dan a
            LEFT JOIN DanhSachNhom dsn ON a.id = dsn.giang_vien_id
            WHERE a.dot_dang_ky_id = :dotDangKyId AND a.co_so_id = :coSoId
            """, nativeQuery = true)
    List<CnGiangVienHuongDanDanhSachNhomResponse> getGiangVienAndDanhSachNhomDangHuongDan(@Param("dotDangKyId") String dotDangKyId,
                                                                                          @Param("coSoId") String coSoId);

}
