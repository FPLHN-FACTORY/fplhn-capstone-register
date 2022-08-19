package com.fpolydatn.core.admin.repository;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.repository.HocKyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author phongtt35
 */
public interface AdHocKyRepository extends HocKyRepository {

    @Query(value = """
        SELECT id, ten_hoc_ky, last_modified_date 
            FROM hoc_ky hk 
            WHERE 
                ( :#{#req.tenHocKy} IS NULL 
                    OR :#{#req.tenHocKy} LIKE '' 
                    OR ten_hoc_ky LIKE %:#{#req.tenHocKy}% ) 
            ORDER BY last_modified_date DESC 
        """, nativeQuery = true)
    Page<HocKyResponse> findByName(@Param("req") FindHocKyRequest req, Pageable page);

    @Query(value = """
        SELECT COUNT(1) FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy%
        """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);
}
