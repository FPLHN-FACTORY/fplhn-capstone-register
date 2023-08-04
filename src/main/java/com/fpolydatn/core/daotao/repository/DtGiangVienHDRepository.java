package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vinhnv
 */
@Repository
public interface DtGiangVienHDRepository extends GiangVienHuongDanRepository {

    @Query(value = """
                SELECT ROW_NUMBER() OVER(ORDER BY gvhd.last_modified_date DESC ) AS stt,
                gvhd.id,
                cs.ten_co_so,
                ddk.ten_dot_dang_ky,
                gvhd.ten_gvhd ,
                gvhd.so_nhom_huong_dan_toi_da,
                COUNT(nd.id) AS so_nhom_dang_huong_dan,
                gvhd.ten_tai_khoan, 
                gvhd.so_dien_thoai,
                gvhd.email_fpt,
                gvhd.email_fe 
                FROM giang_vien_huong_dan gvhd 
                LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE 
                    ( :#{#req.tenGiangVien} IS NULL 
                        OR :#{#req.tenGiangVien} LIKE '' 
                        OR ten_gvhd LIKE %:#{#req.tenGiangVien}% ) 
                 AND cs.id = :#{#req.coSoId}
                 AND ddk.id = :#{#req.dotDangKyId}
                GROUP BY gvhd.id
                ORDER BY gvhd.last_modified_date DESC 
            """, countQuery = """
                SELECT COUNT(gvhd.id)
                FROM giang_vien_huong_dan gvhd 
                LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE 
                     ( :#{#req.tenGiangVien} IS NULL 
                        OR :#{#req.tenGiangVien} LIKE '' 
                        OR ten_gvhd LIKE %:#{#req.tenGiangVien}% ) 
                 AND cs.id = :#{#req.coSoId}
                 AND ddk.id = :#{#req.dotDangKyId}
                GROUP BY gvhd.id
                ORDER BY gvhd.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtGiangVienHDResponse> findByName(@Param("req") DtFindGiangVienHDRequest req, Pageable page);

    @Query(value = """
            SELECT COUNT(1) FROM giang_vien_huong_dan gvhd WHERE gvhd.ten_gvhd LIKE %:tenGiangVien% AND co_so_id = :coSoId
            """, nativeQuery = true)
    Long countByName(@Param("tenGiangVien") String tenGiangVien, @Param("coSoId") String coSoId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fpt = :emailFpt AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdByEmailFpt(@Param("emailFpt") String emailFpt, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fpt = :emailFpt AND id <> :id AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdUpdateByEmailFpt(@Param("emailFpt") String emailFpt, @Param("id") String id, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fe = :emailFe AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdByEmailFe(@Param("emailFe") String emailFe, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fe = :emailFe AND id <> :id AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdUpdateByEmailFe(@Param("emailFe") String emailFe, @Param("id") String id, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan AND id <> :id AND co_so_id = :coSoId AND dot_dang_ky_id = :dotDangkyId", nativeQuery = true)
    String getIdUpdateByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("id") String id, @Param("coSoId") String coSoId, @Param("dotDangkyId") String dotDangkyId);

    @Query(value = """
                  SELECT  COUNT(nd.id) AS so_nhom_dang_huong_dan
                  FROM giang_vien_huong_dan gvhd 
                  LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id
                  LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                  LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id             
                  WHERE gvhd.id = :idGiangVien GROUP BY gvhd.id 
            """, nativeQuery = true)
    int getCountSoNhomDangHuongDan(@Param("idGiangVien") String idGiangVien);

    @Query(value = """
                SELECT ROW_NUMBER() OVER(ORDER BY gvhd.last_modified_date DESC ) AS stt,
                gvhd.id,
                cs.ten_co_so,
                ddk.ten_dot_dang_ky,
                gvhd.ten_gvhd ,
                gvhd.so_nhom_huong_dan_toi_da,
                COUNT(nd.id) AS so_nhom_dang_huong_dan,
                gvhd.ten_tai_khoan, 
                gvhd.so_dien_thoai,
                gvhd.email_fpt,
                gvhd.email_fe 
                FROM giang_vien_huong_dan gvhd 
                LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE 
                 cs.id = :#{#req.coSoId}
                 AND
                 ddk.id = :#{#req.dotDangKyId}
                GROUP BY gvhd.id
                ORDER BY gvhd.last_modified_date DESC 
            """, countQuery = """
                SELECT COUNT(gvhd.id)
                FROM giang_vien_huong_dan gvhd 
                LEFT JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                LEFT JOIN nhom_datn nd ON nd.giang_vien_id = gvhd.id
                LEFT JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE
                 cs.id = :#{#req.coSoId}
                 AND
                 ddk.id = :#{#req.dotDangKyId}
                GROUP BY gvhd.id
                ORDER BY gvhd.last_modified_date DESC 
            """, nativeQuery = true)
    List<DtGiangVienHDResponse> exportExcel(@Param("req") DtFindGiangVienHDRequest req);
    @Query(value = """
            SELECT email_fe FROM giang_vien_huong_dan WHERE co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId""", nativeQuery = true)
    List<String> findAllEmailFe(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT email_fpt FROM giang_vien_huong_dan WHERE co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId""", nativeQuery = true)
    List<String> findAllEmailFpt(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT ten_tai_khoan FROM giang_vien_huong_dan WHERE co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId""", nativeQuery = true)
    List<String> findAllTenTaiKhoan(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);
}
