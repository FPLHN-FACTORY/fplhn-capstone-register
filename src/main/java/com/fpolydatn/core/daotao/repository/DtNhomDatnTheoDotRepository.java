package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DtNhomDatnTheoDotRepository extends NhomDatnRepository {
    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.created_date DESC) AS stt, b.ma_nhom, i.ten_mon_datn
            ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien) AS total_ma_sinh_vien, c.ma_sinh_vien, c.ten_sinh_vien, 
            e.ten_gvhd, e.ten_tai_khoan, b.trang_thai, b.last_modified_date
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id
            LEFT JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id
            WHERE (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND g.id = :#{#req.dotDangKyId}
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id
            ORDER BY b.created_date DESC 
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN chuyen_nganh f ON f.id = c.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id
            WHERE (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND g.id = :#{#req.dotDangKyId}
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            ORDER BY b.created_date DESC 
            """, nativeQuery = true)
    Page<DtNhomDatnTheoDotResponse> findAllNhomDatn(Pageable page, @Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value ="""
                SELECT a.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, 
                a.ma_sinh_vien, a.ten_sinh_vien, IF(a.id = b.truong_nhom_id, 1, 0) AS chuc_vu
                FROM sinh_vien a JOIN nhom_datn b ON a.nhom_id = b.id
                WHERE b.id = :idNhom
                AND a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId     
                """, countQuery = """
                SELECT COUNT(a.id) FROM sinh_vien a JOIN nhom_datn b ON a.nhom_id = b.id
                WHERE b.id = :idNhom
                AND a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId
                """, nativeQuery = true)
    List<DtSinhVienResponse> showDanhSachSinhVien(@Param("idNhom") String idNhom,
                                                  @Param("coSoId") String coSoId,
                                                  @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.created_date DESC) AS stt, b.ma_nhom, i.ten_mon_datn
            ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien) AS total_ma_sinh_vien, c.ma_sinh_vien, c.ten_sinh_vien, 
            e.ten_gvhd, e.ten_tai_khoan, b.trang_thai, b.last_modified_date
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id
            LEFT JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id
            WHERE (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND g.id = :#{#req.dotDangKyId}
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id
            ORDER BY b.created_date DESC 
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id
            LEFT JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id
            WHERE (:#{#req.maNhom} IS NULL 
            OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.chuyenNganhId} IS NULL 
            OR :#{#req.chuyenNganhId} LIKE ''
            OR f.id = :#{#req.chuyenNganhId})
            AND g.id = :#{#req.dotDangKyId}
            AND (:#{#req.trangThai} IS NULL 
            OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id 
            ORDER BY b.created_date DESC 
            """, nativeQuery = true)
    List<DtNhomDatnTheoDotResponse> findAllNhomDatnToExcel(@Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value = """
                SELECT 
                n.id,
                n.ma_nhom AS 'name'
                 FROM do_an_tot_nghiep.nhom_datn n
                 WHERE n.co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMaNhom(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT a.id, a.created_date, a.last_modified_date, a.co_so_id, a.dot_dang_ky_id, a.giang_vien_id, a.ma_nhom, a.mat_khau,
            a.mo_ta, a.nhan_xet, a.ten_de_tai, a.ten_nhom, a.trang_thai, a.truong_nhom_id
            FROM nhom_datn a WHERE a.dot_dang_ky_id = :dotDangKyId AND a.co_so_id = :coSoId
            """, nativeQuery = true)
    List<NhomDatn> findAllByDotDangKyId(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

}
