package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DtNhomDatnTheoDotRepository  extends NhomDatnRepository {
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
            AND (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE ''
            OR g.id = :#{#req.dotDangKyId})
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id
            ORDER BY b.last_modified_date DESC 
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
            AND (:#{#req.dotDangKyId} IS NULL OR :#{#req.dotDangKyId} LIKE ''
            OR g.id = :#{#req.dotDangKyId})
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND b.co_so_id = :#{#req.coSoId}
            GROUP BY b.id 
            ORDER BY b.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtNhomDatnTheoDotResponse> findAllNhomDatn(Pageable page, @Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value = "select a.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, a.ma_sinh_vien, a.ten_sinh_vien from sinh_vien a JOIN nhom_datn b ON a.nhom_id = b.id\n" +
            "        where a.nhom_id = :#{#req.id}" +
            " AND b.co_so_id = :#{#req.coSoId}", nativeQuery = true)
    Page<DtSinhVienResponse> showDanhSachSinhVien(Pageable page, @Param("req") final FindSinhVienRequest req);

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
            GROUP BY b.id, b.ten_nhom ,b.ten_de_tai , f.ten_chuyen_nganh 
            ORDER BY b.last_modified_date DESC 
            """, nativeQuery = true)
    List<DtNhomDatnTheoDotResponse> findAllNhomDatnToExcel();
}
