package com.fpolydatn.core.daotao.reopsitory;

import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
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
            """,nativeQuery = true)
    Page<DtGiangVienHDResponse> findByName(@Param("req") DtFindGiangVienHDRequest req, Pageable page);

    @Query(value = """
            SELECT COUNT(1) FROM giang_vien_huong_dan gvhd WHERE gvhd.ten_gvhd LIKE %:tenGiangVien%
            """, nativeQuery = true)
    Long countByName(@Param("tenGiangVien") String tenGiangVien);



}
