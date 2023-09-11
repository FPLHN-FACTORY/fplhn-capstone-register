package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSimpleDashboardGvhdResponse;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnDashboardRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT GROUP_CONCAT(a.ten_chuyen_nganh SEPARATOR ', ') AS ten_chuyen_nganh
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            WHERE b.id = :idChuNhiem AND b.co_so_id = :coSoId
            """, nativeQuery = true)
    String getTenChuyenNganhByIdChuNhiem(@Param("idChuNhiem") String idChuNhiem, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT COUNT(c.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR c.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND c.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer getTongSoSinhVien(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR d.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer getTongSoNhom(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR d.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId} AND d.trang_thai = 5
            """, nativeQuery = true)
    Integer getTongSoNhomDaChotDeTai(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.id)
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR a.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer tongSoGvhd(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.id)
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR a.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId} AND (SELECT COUNT(g.id) FROM nhom_datn g WHERE g.giang_vien_id = a.id) > 0
            """, nativeQuery = true)
    Integer tongSoGvhdDangCoNhom(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT DISTINCT a.id, CONCAT(a.ten_gvhd, ' - ', a.ten_tai_khoan) as name, a.so_nhom_huong_dan_toi_da
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            WHERE (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE '' OR a.dot_dang_ky_id = :#{#req.dotDangKyId})
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    List<CnSimpleDashboardGvhdResponse> getGvhd(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT a.ma_nhom, COUNT(b.id) as so_luong_thanh_vien FROM nhom_datn a JOIN sinh_vien b ON a.id = b.nhom_id
            WHERE a.giang_vien_id = :idGiangVien AND a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId
            GROUP BY a.id
            """, nativeQuery = true)
    List<CnDashboardNhomDatnResponse> getNhomDatnByIdGiangVien(@Param("idGiangVien") String idGiangVien, @Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT a.id, a.ten_chuyen_nganh AS name 
            FROM chuyen_nganh a WHERE a.co_so_id = :#{#req.coSoId} AND a.chu_nhiem_bo_mon = :#{#req.idChuNhiem}
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(@Param("req") CnFindDashboardRequest req);
}
