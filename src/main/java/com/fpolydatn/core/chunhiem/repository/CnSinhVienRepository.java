package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CnSinhVienRepository extends SinhVienRepository {

    @Query(value = """
        SELECT a.id, ROW_NUMBER() OVER(ORDER BY ten_sinh_vien) AS STT, ma_sinh_vien,
                if(a.id = e.truong_nhom_id, CONCAT(a.ten_sinh_vien, ' (C)'),a.ten_sinh_vien) as ten_sinh_vien,
                (select ma_mon from mon_datn where id = a.mon_chuong_trinh_id ) as 'ma_mon_chuong_trinh',
                (select ma_mon from mon_datn where id = a.mon_datn_id ) as 'ma_mon_du_an'
                ,e.ten_nhom,
                CONCAT(f.ten_gvhd, '-',f.ten_tai_khoan) as ten_gvhd FROM sinh_vien a 
                LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id 
                LEFT JOIN mon_datn c ON c.id = a.mon_datn_id 
                LEFT JOIN dot_dang_ky  d ON d.id = a.dot_dang_ky_id 
                LEFT JOIN nhom_datn e ON e.id= a.nhom_id 
                LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id 
        WHERE e.id = :#{#req.idNhom} AND d.id = :#{#req.dotDangKyId} AND a.co_so_id = :#{#req.coSoId} ORDER BY ten_sinh_vien """,
            countQuery = """ 
                    SELECT COUNT(a.id) FROM sinh_vien a
                    LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id
                    LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
                    LEFT JOIN dot_dang_ky  d ON d.id = a.dot_dang_ky_id
                    LEFT JOIN nhom_datn e ON e.id= a.nhom_id
                    LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id
                    WHERE e.id =  :#{#req.idNhom} AND d.id = :#{#req.dotDangKyId} AND a.co_so_id = :#{#req.coSoId}  ORDER BY ten_sinh_vien""", nativeQuery = true)
    Page<CnSinhVienTheoNhomResponse> searchSinhVien(@Param("req") CnFindSinhVienTheoNhomRequest req, Pageable page);

    @Query(value = "SELECT IF(COUNT(1) IS NULL, 0, COUNT(1)) FROM sinh_vien a WHERE a.nhom_id = :id_nhom AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    Integer countListSinhVien(@Param("id_nhom") String id_nhom, @Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien LIKE :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getIdSinhVienByMaSinhVien(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT * FROM sinh_vien WHERE ma_sinh_vien LIKE :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    SinhVien getSinhVienByMaSinhVien(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien AND nhom_id IS NULL AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getIdByMaSinhVienAndMaNhom(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT mon_chuong_trinh_id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String findIdMonChuongTrinh(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = " SELECT mon_datn_id FROM sinh_vien WHERE id = :idCaptain AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String findIdMonDatnByIdCaptain(@Param("idCaptain") String idCaptain, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien and nhom_id = :idNhom AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    String getIdByMaSinhVienAndIdNhom(@Param("maSinhVien") String maSinhVien, @Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = NULL , mon_datn_id  = NULL WHERE ma_sinh_vien = :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    void deleteSinhVienOutGroup(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :idNhom,mon_datn_id  = :idMonDatn WHERE ma_sinh_vien = :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    void addSinhVienInGroup(@Param("idNhom") String idNhom, @Param("maSinhVien") String maSinhVien, @Param("idMonDatn") String idMondatn, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ten_sinh_vien) AS STT,a.ma_sinh_vien,a.ten_sinh_vien,
            b.ten_chuyen_nganh,f.ten_dot_dang_ky,
            (select ma_mon from mon_datn where id = a.mon_chuong_trinh_id ) as 'ma_mon_chuong_trinh',
            (select ma_mon from mon_datn where id = a.mon_datn_id ) as 'ma_mon_du_an'
            ,d.ma_nhom,d.ten_de_tai,
            CONCAT(e.ten_gvhd, '-',e.ten_tai_khoan) as ten_gvhd
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON b.id = a.chuyen_nganh_id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN nhom_datn d  ON d.id = a.nhom_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = d.giang_vien_id
            LEFT JOIN dot_dang_ky f ON f.id = a.dot_dang_ky_id
            LEFT JOIN can_bo g ON b.chu_nhiem_bo_mon = g.id
            WHERE a.co_so_id = :#{#req.coSoId} 
            AND (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%  
            OR a.ma_sinh_vien LIKE %:#{#req.tenSinhVien}%) 
            AND f.id = :#{#req.idDotDangKy}
            AND g.id = :#{#req.idChuNhiem}
            AND a.trang_thai = 0
            ORDER BY ten_sinh_vien
            """, countQuery = """
            SELECT COUNT(a.id)
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON b.id = a.chuyen_nganh_id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN nhom_datn d  ON d.id = a.nhom_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = d.giang_vien_id
            LEFT JOIN dot_dang_ky f ON f.id = a.dot_dang_ky_id
            LEFT JOIN can_bo g ON b.chu_nhiem_bo_mon = g.id
            WHERE a.co_so_id = :#{#req.coSoId} 
            AND (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%  
            OR a.ma_sinh_vien LIKE %:#{#req.tenSinhVien}%) 
            AND f.id = :#{#req.idDotDangKy}
            AND g.id = :#{#req.idChuNhiem}
            AND a.trang_thai = 0
            ORDER BY ten_sinh_vien
             """, nativeQuery = true)
    Page<CnSinhVienTheoDotResponse> searchSinhVien(@Param("req") CnFindSinhVienTheoDotRequest req, Pageable page);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ten_sinh_vien) AS STT,a.ma_sinh_vien,a.ten_sinh_vien,
               b.ten_chuyen_nganh, c.ma_mon,d.ma_nhom,d.ten_de_tai,e.ten_gvhd
               FROM sinh_vien a
               LEFT JOIN chuyen_nganh b  ON b.id = a.chuyen_nganh_id
               LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
               LEFT JOIN nhom_datn d  ON d.id = a.nhom_id
               LEFT JOIN giang_vien_huong_dan e ON e.id = d.giang_vien_id
               LEFT JOIN dot_dang_ky f ON f.id = a.dot_dang_ky_id
               where a.ten_sinh_vien LIKE  CONCAT('%',:tenSinhVien,'%') AND a.co_so_id = :coSoId AND f.id = :dotDangKyId ORDER BY ten_sinh_vien
               """, nativeQuery = true)
    List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(@Param("tenSinhVien") String tenSinhVien, @Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
             SELECT * FROM sinh_vien WHERE id != :idCaptain and nhom_id = :idGroup AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    List<SinhVien> getListSinhVienNotCaptain(@Param("idCaptain") String idCaptain, @Param("idGroup") String idGroup, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT * FROM sinh_vien WHERE nhom_id = :idNhom AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    List<SinhVien> getAllSinhVien(@Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT CONCAT(a.ma_sinh_vien, '_' , a.ten_sinh_vien) as thong_tin, a.ma_sinh_vien FROM sinh_vien a JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE ((:#{#req.maSinhVien} IS NULL OR :#{#req.maSinhVien} LIKE ''
            OR a.ma_sinh_vien LIKE %:#{#req.maSinhVien}%)
            OR (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%))
            AND a.co_so_id = :#{#req.coSoId} AND a.dot_dang_ky_id = :#{#req.dotDangKyId} AND a.nhom_id IS NULL AND a.trang_thai = 0
            AND (SELECT m.nhom_mon_datn_id FROM mon_datn m WHERE m.id = a.mon_chuong_trinh_id) = :#{#req.nhomMonDatnId}
            AND b.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<CnSinhVienSearchResponse> getListSinhVienNoGroup(@Param("req") CnFindSinhVienNoGroupRequest req, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT CONCAT(a.ma_sinh_vien, '_' , a.ten_sinh_vien) as thong_tin FROM sinh_vien a JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE ((:#{#req.maSinhVien} IS NULL OR :#{#req.maSinhVien} LIKE ''
            OR a.ma_sinh_vien LIKE %:#{#req.maSinhVien}%)
            OR (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%))
            AND a.co_so_id = :#{#req.coSoId} AND a.dot_dang_ky_id = :#{#req.dotDangKyId} AND a.nhom_id IS NULL
            AND b.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<String> getStringListSinhVienNoGroup(@Param("req") CnFindSinhVienNoGroupRequest req, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT a.id FROM sinh_vien a WHERE (SELECT b.nhom_mon_datn_id FROM mon_datn b WHERE b.id = :monDatnId)
            = (SELECT c.nhom_mon_datn_id FROM mon_datn c WHERE c.id = a.mon_chuong_trinh_id)
            AND a.id = :sinhVienId AND a.dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String checkMonTuongDuong(@Param("monDatnId") String monDatnId, @Param("sinhVienId") String sinhVienId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT * FROM sinh_vien a LEFT JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE a.dot_dang_ky_id = :dotDangKyId AND a.co_so_id = :coSoId AND b.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<SinhVien> getALlSinhVienByIdChuNhiem(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT * FROM sinh_vien a LEFT JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            LEFT JOIN nhom_datn c ON a.nhom_id = c.id WHERE c.truong_nhom_id = a.id 
            AND a.dot_dang_ky_id = :dotDangKyId AND a.co_so_id = :coSoId AND b.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<SinhVien> getALlTruongNhomByIdChuNhiem(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

}
