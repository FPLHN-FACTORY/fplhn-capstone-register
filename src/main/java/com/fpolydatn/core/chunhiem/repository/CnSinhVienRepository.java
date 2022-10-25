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
            b.ten_chuyen_nganh,d.ten_dot_dang_ky,e.ten_nhom,f.ten_gvhd FROM sinh_vien a 
            LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id 
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id 
            LEFT JOIN dot_dang_ky  d ON d.id = a.dot_dang_ky_id 
            LEFT JOIN nhom_datn e ON e.id= a.nhom_id 
            LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id 
            WHERE e.id = :#{#req.idNhom} AND a.co_so_id = :#{#req.coSoId} ORDER BY ten_sinh_vien """,
            countQuery = """ 
                    SELECT COUNT(a.id) FROM sinh_vien a
                    LEFT JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id
                    LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
                    LEFT JOIN dot_dang_ky  d ON d.id = a.dot_dang_ky_id
                    LEFT JOIN nhom_datn e ON e.id= a.nhom_id
                    LEFT JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id
                    WHERE e.id =  :#{#req.idNhom} AND a.co_so_id = :#{#req.coSoId}  ORDER BY ten_sinh_vien""", nativeQuery = true)
    Page<CnSinhVienTheoNhomResponse> searchSinhVien(@Param("req") CnFindSinhVienTheoNhomRequest req, Pageable page);

    @Query(value = "SELECT IF(COUNT(1) IS NULL, 0, COUNT(1)) FROM sinh_vien a WHERE a.nhom_id = :id_nhom AND co_so_id = :coSoId", nativeQuery = true)
    Integer countListSinhVien(@Param("id_nhom") String id_nhom, @Param("coSoId") String coSoId);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien LIKE :maSinhVien", nativeQuery = true)
    String getIdSinhVienByMaSinhVien(@Param("maSinhVien") String maSinhVien);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien and nhom_id is null", nativeQuery = true)
    String getIdByMaSinhVienAndMaNhom(@Param("maSinhVien") String maSinhVien);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien and nhom_id = :idNhom", nativeQuery = true)
    String getIdByMaSinhVienAndidNhom(@Param("maSinhVien") String maSinhVien, @Param("idNhom") String idNhom);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = NULL WHERE ma_sinh_vien = :maSinhVien", nativeQuery = true)
    void deleteSinhVienOutGroup(@Param("maSinhVien") String maSinhVien);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :idNhom WHERE ma_sinh_vien = :maSinhVien", nativeQuery = true)
    void addSinhVienInGroup(@Param("idNhom") String idNhom, @Param("maSinhVien") String maSinhVien);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ten_sinh_vien) AS STT,a.ma_sinh_vien,a.ten_sinh_vien,
            b.ten_chuyen_nganh,f.ten_dot_dang_ky, c.ma_mon,d.ma_nhom,d.ten_de_tai,e.ten_gvhd
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON b.id = a.chuyen_nganh_id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN nhom_datn d  ON d.id = a.nhom_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = d.giang_vien_id
            LEFT JOIN dot_dang_ky f ON f.id = a.dot_dang_ky_id
            WHERE a.co_so_id = :#{#req.coSoId} 
            AND (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%)
            AND (:#{#req.idDotDangKy} IS NULL OR :#{#req.idDotDangKy} LIKE ''
                    OR f.id = :#{#req.idDotDangKy})
             ORDER BY ten_sinh_vien
            """, countQuery = """
            SELECT COUNT(a.id)
            FROM sinh_vien a
            LEFT JOIN chuyen_nganh b  ON b.id = a.chuyen_nganh_id
            LEFT JOIN mon_datn c ON c.id = a.mon_datn_id
            LEFT JOIN nhom_datn d  ON d.id = a.nhom_id
            LEFT JOIN giang_vien_huong_dan e ON e.id = d.giang_vien_id
            LEFT JOIN dot_dang_ky f ON f.id = a.dot_dang_ky_id
            WHERE f.id = :#{#req.idDotDangKy} AND a.co_so_id = :#{#req.coSoId}
            AND (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR a.ten_sinh_vien LIKE %:#{#req.tenSinhVien}%) ORDER BY ten_sinh_vien
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
               where a.ten_sinh_vien LIKE  CONCAT('%',:tenSinhVien,'%') AND a.co_so_id = :coSoId  ORDER BY ten_sinh_vien
               """, nativeQuery = true)
    List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(@Param("tenSinhVien") String tenSinhVien, @Param("coSoId") String coSoId);

    @Query(value = """
             SELECT * FROM sinh_vien WHERE id != :idCaptain and nhom_id = :idGroup
            """, nativeQuery = true)
    List<SinhVien> getListSinhVienNotCaptain(@Param("idCaptain") String idCaptain, @Param("idGroup") String idGroup);

    @Query(value = "SELECT * FROM sinh_vien WHERE nhom_id = :idNhom", nativeQuery = true)
    List<SinhVien> getAllSinhVien(String idNhom);

    @Query(value = """
            SELECT CONCAT(ma_sinh_vien, '_' , ten_sinh_vien) as thong_tin, ma_sinh_vien FROM sinh_vien
            WHERE ((:#{#req.maSinhVien} IS NULL OR :#{#req.maSinhVien} LIKE ''
            OR ma_sinh_vien LIKE %:#{#req.maSinhVien}%)
            OR (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR ten_sinh_vien LIKE %:#{#req.tenSinhVien}%))
            AND co_so_id = :#{#req.coSoId} AND dot_dang_ky_id = :#{#req.dotDangKyId} AND nhom_id IS NULL
            """, nativeQuery = true)
    List<CnSinhVienSearchResponse> getListSinhVienNoGroup(@Param("req") CnFindSinhVienNoGroupRequest req);

    @Query(value = """
            SELECT CONCAT(ma_sinh_vien, '_' , ten_sinh_vien) as thong_tin FROM sinh_vien
            WHERE ((:#{#req.maSinhVien} IS NULL OR :#{#req.maSinhVien} LIKE ''
            OR ma_sinh_vien LIKE %:#{#req.maSinhVien}%)
            OR (:#{#req.tenSinhVien} IS NULL OR :#{#req.tenSinhVien} LIKE ''
            OR ten_sinh_vien LIKE %:#{#req.tenSinhVien}%))
            AND co_so_id = :#{#req.coSoId} AND dot_dang_ky_id = :#{#req.dotDangKyId} AND nhom_id IS NULL
            """, nativeQuery = true)
    List<String> getStringListSinhVienNoGroup(@Param("req") CnFindSinhVienNoGroupRequest req);

    @Query(value = """
            SELECT a.id FROM sinh_vien a WHERE (SELECT b.nhom_mon_datn_id FROM mon_datn b WHERE b.id = :monDatnId)
            = (SELECT c.nhom_mon_datn_id FROM mon_datn c WHERE c.id = a.mon_chuong_trinh_id)
            AND a.id = :sinhVienId
            """, nativeQuery = true)
    String checkMonTuongDuong(@Param("monDatnId") String monDatnId, @Param("sinhVienId") String sinhVienId);
}
