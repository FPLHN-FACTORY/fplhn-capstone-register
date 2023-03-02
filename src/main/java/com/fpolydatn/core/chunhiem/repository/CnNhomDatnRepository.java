package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotExcel;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author thangncph26123
 */
@Transactional
public interface CnNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ma_nhom
            ,b.ten_de_tai, k.ma_mon AS ma_mon_datn, COUNT(d.ma_sinh_vien)
            AS total_ma_sinh_vien, e.ten_gvhd, e.ten_tai_khoan, g.ten_dot_dang_ky, b.trang_thai, b.last_modified_date
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN mon_datn k ON k.id = c.mon_datn_id
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id
            LEFT JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%)
            AND (:#{#req.monDatnId} IS NULL OR :#{#req.monDatnId} LIKE ''
            OR k.id = :#{#req.monDatnId})
            AND (:#{#req.tenTaiKhoan} IS NULL OR :#{#req.tenTaiKhoan} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.tenTaiKhoan}% OR e.ten_tai_khoan LIKE %:#{#req.tenTaiKhoan}%)
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND g.id = :#{#req.dotDangKyId}
            AND b.co_so_id = :#{#req.coSoId}
            AND h.id = :idChuNhiem
            GROUP BY b.id ORDER BY b.last_modified_date DESC 
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b LEFT JOIN sinh_vien c ON b.truong_nhom_id = c.id
            LEFT JOIN mon_datn k ON k.id = c.mon_datn_id
            LEFT JOIN sinh_vien d ON d.nhom_id = b.id
            LEFT JOIN chuyen_nganh f ON f.id = c.chuyen_nganh_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            LEFT JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            LEFT JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR b.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%)
            AND (:#{#req.monDatnId} IS NULL OR :#{#req.monDatnId} LIKE ''
            OR k.id = :#{#req.monDatnId})
            AND (:#{#req.tenTaiKhoan} IS NULL OR :#{#req.tenTaiKhoan} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.tenTaiKhoan}% OR e.ten_tai_khoan LIKE %:#{#req.tenTaiKhoan}%)
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            AND g.id = :#{#req.dotDangKyId}
            AND b.co_so_id = :#{#req.coSoId}
            AND h.id = :idChuNhiem
            GROUP BY b.id ORDER BY b.created_date DESC 
            """, nativeQuery = true)
    Page<CnNhomDatnResponse> findNhomDatn(@Param("req") CnFindNhomDatnRequest req, Pageable page, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ten_sinh_vien) AS STT, ma_sinh_vien,
            a.ten_sinh_vien as ten_sinh_vien, e.ma_nhom,
            if(a.id = e.truong_nhom_id, 'x', '') as chuc_vu
            , e.ten_de_tai, f.ten_tai_khoan AS 'gvhd', d.ten_dot_dang_ky
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id
            LEFT JOIN can_bo g ON b.chu_nhiem_bo_mon = g.id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN dot_dang_ky d ON d.id = a.dot_dang_ky_id
            LEFT JOIN nhom_datn e ON e.id= a.nhom_id
            LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id
            WHERE d.id = :#{#req.dotDangKyId} AND a.co_so_id = :#{#req.coSoId} AND g.id = :idChuNhiem
            AND a.trang_thai = :trangThai
            """, countQuery = """
            SELECT COUNT(a.id)
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id
            LEFT JOIN can_bo g ON b.chu_nhiem_bo_mon = g.id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN dot_dang_ky d ON d.id = a.dot_dang_ky_id
            LEFT JOIN nhom_datn e ON e.id= a.nhom_id
            LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id
            WHERE d.id = :#{#req.dotDangKyId} AND a.co_so_id = :#{#req.coSoId} AND g.id = :idChuNhiem
            """, nativeQuery = true)
    List<CnSinhVienTheoDotExcel> findNhomDatnExport(@Param("req") CnFindNhomDatnRequest req, @Param("idChuNhiem") String idChuNhiem, @Param("trangThai") int trangThai);

    @Query(value = "SELECT id FROM nhom_datn WHERE ma_nhom = :maNhom AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getIdByMaNhom(@Param("maNhom") String maNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT count(a.ma_sinh_vien) as so_luong FROM sinh_vien a
            WHERE nhom_id = :idNhom AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    int getSoLuongThanhVienNhom(@Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT so_nhom_huong_dan_toi_da FROM giang_vien_huong_dan WHERE id = :id AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    int getSoNhomGiangVienHuongDan(@Param("id") String id, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE nhom_datn SET giang_vien_id = :giangVienId where id = :idNhom AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    void addGvhd(@Param("giangVienId") String giangVienId, @Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien AND nhom_id IS NULL AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getSinhVienByMaSinhVienAndMaNhom(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getSinhVienByMaSinhVien(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getIdByTenTaiKhoanGvhd(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :nhomId, mon_datn_id = :monDatnId WHERE id = :id AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    void setNhomForTruongNhom(@Param("nhomId") String nhomId, @Param("monDatnId") String monDatnId, @Param("id") String id, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT truong_nhom_id FROM nhom_datn where id = :idNhom AND dot_dang_ky_id = :dotDangKyId
             """, nativeQuery = true)
    String findIDTruongNhom(@Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = """
              UPDATE nhom_datn SET truong_nhom_id = :truongNhomId WHERE id = :idNhom AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    void changeCaptainByTruongNhomId(@Param("truongNhomId") String truongNhomId, @Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT * FROM nhom_datn where ma_nhom = :maNhom AND dot_dang_ky_id = :dotDangKyId
             """, nativeQuery = true)
    NhomDatn getNhomDatnByMaNhom(@Param("maNhom") String maNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT id, ma_nhom as 'name' FROM nhom_datn WHERE dot_dang_ky_id = :dotDangKyId and co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllNhomDatn(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT * FROM nhom_datn a LEFT JOIN sinh_vien b ON a.truong_nhom_id = b.id
            LEFT JOIN chuyen_nganh c ON b.chuyen_nganh_id = c.id
            WHERE a.dot_dang_ky_id = :dotDangKyId AND a.co_so_id = :coSoId AND c.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<NhomDatn> getAllNhomDatnByIdChuNhiem(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);
}
