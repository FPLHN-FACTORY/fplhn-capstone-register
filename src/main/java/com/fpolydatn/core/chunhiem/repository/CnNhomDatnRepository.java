package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ten_nhom
            ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien)
            AS total_ma_sinh_vien, c.ma_sinh_vien, e.ten_gvhd, e.ten_tai_khoan, b.trang_thai, b.last_modified_date
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenNhom} IS NULL OR :#{#req.tenNhom} LIKE ''
            OR b.ten_nhom LIKE %:#{#req.tenNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.tenGvhd}% OR e.ten_tai_khoan LIKE %:#{#req.tenGvhd}%)
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id ORDER BY b.last_modified_date DESC 
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenNhom} IS NULL OR :#{#req.tenNhom} LIKE ''
            OR b.ten_nhom LIKE %:#{#req.tenNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.tenGvhd}% OR e.ten_tai_khoan LIKE %:#{#req.tenGvhd}%)
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id ORDER BY b.last_modified_date DESC 
            """, nativeQuery = true)
    Page<CnNhomDatnResponse> findNhomDatn(@Param("req") CnFindNhomDatnRequest req, Pageable page);

    @Query(value = "SELECT id FROM nhom_datn WHERE ma_nhom = :maNhom",nativeQuery = true)
    String getIdByMaNhom(@Param("maNhom") String maNhom);

    @Query(value = """
            SELECT count(a.ma_sinh_vien) as so_luong FROM sinh_vien a
            WHERE nhom_id = :idNhom""",nativeQuery = true)
    int getSoLuongThanhVienNhom(@Param("idNhom")String idNhom);

}
