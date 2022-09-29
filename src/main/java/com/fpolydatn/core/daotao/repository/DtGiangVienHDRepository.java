package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
                gvhd.ten_tai_khoan, 
                gvhd.so_dien_thoai,
                gvhd.email_fpt,
                gvhd.email_fe 
                FROM giang_vien_huong_dan gvhd 
                JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE 
                    ( :#{#req.tenGiangVien} IS NULL 
                        OR :#{#req.tenGiangVien} LIKE '' 
                        OR ten_gvhd LIKE %:#{#req.tenGiangVien}% ) 
                 AND
                     ( :#{#req.coSoId} IS NULL 
                        OR :#{#req.coSoId} LIKE '' 
                        OR cs.id   LIKE %:#{#req.coSoId}% ) 
                ORDER BY gvhd.last_modified_date DESC 
            """, countQuery = """
                SELECT COUNT(gvhd.id)
                FROM giang_vien_huong_dan gvhd 
                JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                JOIN co_so cs ON gvhd.co_so_id = cs.id                
                WHERE 
                    ( :#{#req.tenGiangVien} IS NULL 
                        OR :#{#req.tenGiangVien} LIKE '' 
                        OR ten_gvhd LIKE %:#{#req.tenGiangVien}% ) 
                 AND
                     ( :#{#req.coSoId} IS NULL 
                        OR :#{#req.coSoId} LIKE '' 
                        OR cs.id   LIKE %:#{#req.coSoId}% ) 
                ORDER BY gvhd.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtGiangVienHDResponse> findByName(@Param("req") DtFindGiangVienHDRequest req, Pageable page);

    @Query(value = """
            SELECT COUNT(1) FROM giang_vien_huong_dan gvhd WHERE gvhd.ten_gvhd LIKE %:tenGiangVien%
            """, nativeQuery = true)
    Long countByName(@Param("tenGiangVien") String tenGiangVien);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fpt = :emailFpt", nativeQuery = true)
    String getIdByEmailFpt(@Param("emailFpt") String emailFpt);

    @Query(value = "SELECT * FROM giang_vien_huong_dan WHERE email_fpt = :emailFpt", nativeQuery = true)
    GiangVienHuongDan getGVHDByEmailFpt(@Param("emailFpt") String emailFpt);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fpt = :emailFpt AND id <> :id", nativeQuery = true)
    String getIdUpdateByEmailFpt(@Param("emailFpt") String emailFpt, @Param("id") String id);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fe = :emailFe", nativeQuery = true)
    String getIdByEmailFe(@Param("emailFe") String emailFe);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE email_fe = :emailFe AND id <> :id", nativeQuery = true)
    String getIdUpdateByEmailFe(@Param("emailFe") String emailFe, @Param("id") String id);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan ", nativeQuery = true)
    String getIdByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan);

    @Query(value = "SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan AND id <> :id", nativeQuery = true)
    String getIdUpdateByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("id") String id);

}
