package com.fpolydatn.core.daotao.reopsitory;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.daotao.model.request.FindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.GiangVienHDResponse;
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
        SELECT id, ten_hoc_ky, last_modified_date 
            FROM hoc_ky hk 
            WHERE 
                ( :#{#req.tenGiangVien} IS NULL 
                    OR :#{#req.tenGiangVien} LIKE '' 
                    OR ten_gvhd LIKE %:#{#req.tenGiangVien}% ) 
            ORDER BY last_modified_date DESC 
        """, nativeQuery = true)
    Page<GiangVienHDResponse> findByName(@Param("req") FindGiangVienHDRequest req, Pageable page);

    @Query(value = """
            SELECT COUNT(1) FROM giang_vien_huong_dan gvhd WHERE gvhd.ten_gvhd LIKE %:tenGiangVien%
            """, nativeQuery = true)
    Long countByName(@Param("tenGiangVien") String tenGiangVien);

}
