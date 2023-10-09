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
            WHERE c.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND c.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer getTongSoSinhVien(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT SUM(subquery.count_total)
            FROM (
            SELECT COUNT(c.id) AS count_total
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.nhom_id = d.id
            WHERE c.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND c.co_so_id = :#{#req.coSoId}
            GROUP BY d.id HAVING COUNT(c.id) > 3
            ) AS subquery;
            """, nativeQuery = true)
    Integer getTongSinhVienDaCoNhomHopLe(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT SUM(subquery.count_total)
            FROM (
            SELECT COUNT(c.id) AS count_total
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.nhom_id = d.id
            WHERE c.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND c.co_so_id = :#{#req.coSoId}
            GROUP BY d.id HAVING COUNT(c.id) <= 3
            ) AS subquery;
            """, nativeQuery = true)
    Integer getTongSinhVienChuaCoNhomHopLe(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE d.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer getTongSoNhom(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE d.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            AND d.giang_vien_id IS NULL
            """, nativeQuery = true)
    Integer getTongSoNhomChuaCoGvhd(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE d.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            AND d.ten_de_tai IS NULL
            """, nativeQuery = true)
    Integer getTongSoNhomChuaDatTenDeTai(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(d.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            JOIN nhom_datn d ON c.id = d.truong_nhom_id
            WHERE d.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId} AND d.trang_thai = 5
            """, nativeQuery = true)
    Integer getTongSoNhomDaChotDeTai(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.id)
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            WHERE a.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            """, nativeQuery = true)
    Integer tongSoGvhd(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.id)
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            WHERE a.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId} AND (SELECT COUNT(g.id) FROM nhom_datn g WHERE g.giang_vien_id = a.id) > 0
            """, nativeQuery = true)
    Integer tongSoGvhdDangCoNhom(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            WITH gvhdNhomDatn AS (
                SELECT b.id, COUNT(DISTINCT a.id) AS so_nhom_dang_huong_dan
                FROM nhom_datn a RIGHT JOIN giang_vien_huong_dan b ON a.giang_vien_id = b.id
                JOIN phan_cong_huong_dan c ON b.id = c.giang_vien_id
                JOIN mon_datn d ON c.mon_datn_id = d.id JOIN chuyen_nganh e ON e.id = d.chuyen_nganh_id
                JOIN can_bo f ON f.id = e.chu_nhiem_bo_mon
                WHERE b.dot_dang_ky_id = :#{#req.dotDangKyId}
                AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR e.id = :#{#req.chuyenNganhId})
                AND f.id = :#{#req.idChuNhiem} AND b.co_so_id = :#{#req.coSoId}
                AND (:#{#req.maGvhd} IS NULL OR :#{#req.maGvhd} LIKE '' OR b.ten_tai_khoan LIKE %:#{#req.maGvhd}% OR b.ten_gvhd LIKE %:#{#req.maGvhd}%)
                GROUP BY b.id 
            ), gvhdMonDatn AS (
                SELECT DISTINCT a.id, GROUP_CONCAT(c.ma_mon SEPARATOR ', ') AS mon_datn
                FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
                LEFT JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
                JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
                LEFT JOIN gvhdNhomDatn gvhd ON gvhd.id = a.id
                WHERE a.dot_dang_ky_id = :#{#req.dotDangKyId}
                AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
                AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
                AND (:#{#req.maGvhd} IS NULL OR :#{#req.maGvhd} LIKE '' OR a.ten_tai_khoan LIKE %:#{#req.maGvhd}% OR a.ten_gvhd LIKE %:#{#req.maGvhd}%)
                AND (:#{#req.soNhomHuongDanToiDa} IS NULL OR :#{#req.soNhomHuongDanToiDa} LIKE '' OR gvhd.so_nhom_dang_huong_dan = :#{#req.soNhomHuongDanToiDa} OR a.so_nhom_huong_dan_toi_da = :#{#req.soNhomHuongDanToiDa})
                GROUP BY a.id
            )
            SELECT DISTINCT a.id, CONCAT(a.ten_gvhd, ' - ', a.ten_tai_khoan) as name, a.so_nhom_huong_dan_toi_da,
            gvhdMon.mon_datn AS mon_datn
            FROM giang_vien_huong_dan a JOIN phan_cong_huong_dan b ON a.id = b.giang_vien_id
            LEFT JOIN mon_datn c ON c.id = b.mon_datn_id JOIN chuyen_nganh d ON d.id = c.chuyen_nganh_id
            JOIN can_bo e ON e.id = d.chu_nhiem_bo_mon
            LEFT JOIN gvhdNhomDatn gvhd ON gvhd.id = a.id
            LEFT JOIN gvhdMonDatn gvhdMon ON gvhdMon.id = a.id
            WHERE a.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR d.id = :#{#req.chuyenNganhId})
            AND e.id = :#{#req.idChuNhiem} AND a.co_so_id = :#{#req.coSoId}
            AND (:#{#req.maGvhd} IS NULL OR :#{#req.maGvhd} LIKE '' OR a.ten_tai_khoan LIKE %:#{#req.maGvhd}% OR a.ten_gvhd LIKE %:#{#req.maGvhd}%)
            AND (:#{#req.monDatnId} IS NULL OR :#{#req.monDatnId} LIKE '' OR c.id = :#{#req.monDatnId})
            AND (:#{#req.soNhomHuongDanToiDa} IS NULL OR :#{#req.soNhomHuongDanToiDa} LIKE '' OR gvhd.so_nhom_dang_huong_dan = :#{#req.soNhomHuongDanToiDa} OR a.so_nhom_huong_dan_toi_da = :#{#req.soNhomHuongDanToiDa})
            GROUP BY a.id
            ORDER BY
              CASE WHEN :#{#req.sort} = 'sortDesc' THEN gvhd.so_nhom_dang_huong_dan END DESC,
              CASE WHEN :#{#req.sort} = 'sortAsc' THEN gvhd.so_nhom_dang_huong_dan END ASC,
              CASE WHEN :#{#req.sort} = 'sortMaxDesc' THEN a.so_nhom_huong_dan_toi_da END DESC,
              CASE WHEN :#{#req.sort} = 'sortMaxAsc' THEN a.so_nhom_huong_dan_toi_da END ASC
            """, nativeQuery = true)
    List<CnSimpleDashboardGvhdResponse> getGvhd(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT a.ma_nhom, a.ten_de_tai, COUNT(b.id) as so_luong_thanh_vien, 
            CONCAT(c.ma_mon, ' - ', c.ten_mon_datn) AS mon_datn
            FROM nhom_datn a JOIN sinh_vien b ON a.id = b.nhom_id
            JOIN mon_datn c ON b.mon_datn_id = c.id
            WHERE a.giang_vien_id = :idGiangVien AND a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId
            AND (:tenDeTaiSearch IS NULL OR :tenDeTaiSearch LIKE '' OR IF(:tenDeTaiSearch = 'chuaCoTenDeTai', a.ten_de_tai IS NULL, a.ten_de_tai IS NOT NULL))
            GROUP BY a.id
            """, nativeQuery = true)
    List<CnDashboardNhomDatnResponse> getNhomDatnByIdGiangVien(@Param("idGiangVien") String idGiangVien,
                                                               @Param("dotDangKyId") String dotDangKyId,
                                                               @Param("coSoId") String coSoId,
                                                               @Param("tenDeTaiSearch") String tenDeTaiSearch
    );

    @Query(value = """
            SELECT a.id, a.ten_chuyen_nganh AS name 
            FROM chuyen_nganh a WHERE a.co_so_id = :#{#req.coSoId} AND a.chu_nhiem_bo_mon = :#{#req.idChuNhiem}
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT a.id, CONCAT(a.ma_mon, ' - ', a.ten_mon_datn) AS name
            FROM mon_datn a JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE a.co_so_id = :#{#req.coSoId} AND b.chu_nhiem_bo_mon = :#{#req.idChuNhiem}
            AND a.nhom_mon_datn_id IS NOT NULL
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMonDatnByIdChuNhiem(@Param("req") CnFindDashboardRequest req);

    @Query(value = """
            SELECT COUNT(c.id)
            FROM chuyen_nganh a JOIN can_bo b ON a.chu_nhiem_bo_mon = b.id
            JOIN sinh_vien c ON c.chuyen_nganh_id = a.id
            WHERE c.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE '' OR a.id = :#{#req.chuyenNganhId})
            AND b.id = :#{#req.idChuNhiem} AND c.co_so_id = :#{#req.coSoId} AND c.trang_thai = 1
            """, nativeQuery = true)
    Integer getTongSoSinhVienKhongDuDieuKien(@Param("req") CnFindDashboardRequest req);
}
