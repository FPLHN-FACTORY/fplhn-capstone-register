package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hoangnt
 */
public interface SvNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT a.id, b.id AS id_sinh_vien, ROW_NUMBER() OVER() AS Stt, ma_nhom, ten_nhom, ten_dot_dang_ky, 
            ten_de_tai, ma_sinh_vien, ten_sinh_vien, ten_gvhd, a.mat_khau, 
            (SELECT CONCAT(e.ma_mon, ' - ', e.ten_mon_datn) FROM mon_datn e WHERE e.id = b.mon_chuong_trinh_id) as ten_mon_chuong_trinh, 
            CONCAT(f.ma_mon, ' - ', f.ten_mon_datn) AS ten_mon_datn, 
            IF(b.id = a.truong_nhom_id, 1, 0) AS chuc_vu, a.trang_thai, 
            IF(a.nhan_xet IS NULL, 'Không có nhận xét', a.nhan_xet) AS nhan_xet 
            FROM nhom_datn a LEFT JOIN sinh_vien b ON a.id = b.nhom_id 
            LEFT JOIN giang_vien_huong_dan c ON c.id = a.giang_vien_id 
            JOIN mon_datn f ON f.id = b.mon_datn_id 
            LEFT JOIN dot_dang_ky d ON d.id = a.dot_dang_ky_id 
            WHERE a.id = :nhomDatnId AND a.co_so_id = :coSoId AND a.dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    List<SvDetailNhomDatnResponse> getDetailNhomDatnById(@Param("nhomDatnId") String nhomDatnId,
                                                         @Param("coSoId") String coSoId,
                                                         @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT id, created_date, last_modified_date, co_so_id, dot_dang_ky_id, giang_vien_id, ma_nhom, 
            mat_khau, mo_ta, nhan_xet, ten_de_tai, ten_nhom, trang_thai, truong_nhom_id FROM nhom_datn 
            WHERE truong_nhom_id = :id AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    NhomDatn getNhomDatnByTruongNhomId(@Param("id") String id,
                                       @Param("coSoId") String coSoId,
                                       @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT truong_nhom_id FROM nhom_datn 
            WHERE id = :nhomDatnId and co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String getIdTruongNhom(@Param("nhomDatnId") String nhomDatnId,
                           @Param("coSoId") String coSoId,
                           @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT CONVERT(trang_thai , UNSIGNED) FROM nhom_datn WHERE id = :id AND co_so_id = :coSoId", nativeQuery = true)
    int getTrangThai(@Param("id") String id, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ma_nhom, b.ten_nhom
            ,b.ten_de_tai, i.ten_mon_datn , f.ten_chuyen_nganh, COUNT(d.ma_sinh_vien) AS total_ma_sinh_vien, c.ten_sinh_vien,
            e.ten_gvhd, e.ten_tai_khoan, b.last_modified_date, 
            IF(b.mat_khau IS NULL, 0, 1) AS mat_khau_ton_tai, b.nhan_xet, c.thoi_diem_roi_nhom_gan_nhat 
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id 
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id 
            LEFT JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id 
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id 
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id 
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id 
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE '' 
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%) 
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE '' 
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%) 
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE '' 
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId} 
            AND g.id = :#{#req.dotDangKyId} 
            AND b.trang_thai = 0 
            AND (SELECT i.nhom_mon_datn_id FROM mon_datn i WHERE i.id = d.mon_datn_id) = :idNhomMon 
            GROUP BY b.id 
            HAVING (:#{#req.soLuong} IS NULL OR :#{#req.soLuong} LIKE '' 
            OR CONVERT(COUNT(d.ma_sinh_vien), UNSIGNED) = CONVERT(:#{#req.soLuong}, UNSIGNED))  AND COUNT(d.ma_sinh_vien) < 7 
            ORDER BY b.last_modified_date DESC
            """, countQuery = """
            SELECT count(b.id) 
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id 
            LEFT JOIN chuyen_nganh f ON f.id = c.chuyen_nganh_id 
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id 
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id 
            LEFT JOIN mon_datn i ON i.id = c.mon_datn_id 
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE '' 
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%)  
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE '' 
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%) 
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE '' 
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId} 
            AND g.id = :#{#req.dotDangKyId} 
            AND b.trang_thai = 0 
            AND (SELECT i.nhom_mon_datn_id FROM mon_datn i WHERE i.id = c.mon_datn_id) = :idNhomMon 
            GROUP BY b.id
            HAVING (:#{#req.soLuong} IS NULL OR :#{#req.soLuong} LIKE '' 
            OR CONVERT((select count(sv.id) from nhom_datn mn join sinh_vien sv on mn.id = sv.nhom_id 
               where b.id = mn.id), UNSIGNED) = CONVERT(:#{#req.soLuong}, UNSIGNED)) 
               AND (select count(sv.id) from nhom_datn mn join sinh_vien sv on mn.id = sv.nhom_id 
               where b.id = mn.id ) < 7
            ORDER BY b.last_modified_date DESC
            """, nativeQuery = true)
    Page<SvNhomDatnResponse> findAllNhomDatn(Pageable page, @Param("req") SvFindNhomDatnRequest req,
                                             @Param("idNhomMon") String idNhomMon);

    @Query(value = """
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt,
            a.ma_sinh_vien, a.ten_sinh_vien, b.ma_nhom, b.ten_nhom, b.mo_ta, 
            IF(a.id = b.truong_nhom_id, 1, 0) AS chuc_vu 
            FROM sinh_vien a LEFT JOIN nhom_datn b ON a.nhom_id = b.id 
            WHERE a.nhom_id = :nhomDatnId AND a.co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    List<SvShowSinhVienTheoNhomResponse> getAllSinhVienByNhomDatnId(@Param("nhomDatnId") String nhomDatnId,
                                                                    @Param("coSoId") String coSoId,
                                                                    @Param("dotDangKyId") String dotDangKyId);

}
