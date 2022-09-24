package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SvSinhVienNhomDatnRepository extends SinhVienRepository {
    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ten_nhom
            ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien) AS total_ma_sinh_vien, c.ma_sinh_vien, c.ten_sinh_vien,
            e.ten_gvhd, e.ten_tai_khoan, b.trang_thai, b.last_modified_date, b.mat_khau
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%) 
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE ''
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%)
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId}
            AND B.trang_thai = 0
            GROUP BY b.id
            HAVING COUNT(d.ma_sinh_vien) < 7
            
            ORDER BY b.last_modified_date DESC
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%) 
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE ''
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%)
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId}
            AND B.trang_thai = 0
            GROUP BY b.id
            HAVING COUNT(d.ma_sinh_vien) < 7
            
            ORDER BY b.last_modified_date DESC
            """, nativeQuery = true)
    Page<SvSinhVienNhomDatnResponse> findAllNhomDatn(Pageable page, @Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value = "select a.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, a.ma_sinh_vien, a.ten_sinh_vien from sinh_vien a JOIN nhom_datn b ON a.nhom_id = b.id\n" +
            "        where a.nhom_id = :#{#req.id}" +
            " AND b.co_so_id = :#{#req.coSoId}", nativeQuery = true)
    Page<DtSinhVienResponse> showDanhSachSinhVien(Pageable page, @Param("req") final FindSinhVienRequest req);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :idNhom WHERE id = :id",nativeQuery = true)
    void addSinhVienInGroup(@Param("idNhom") String idNhom, @Param("id") String id);

}
