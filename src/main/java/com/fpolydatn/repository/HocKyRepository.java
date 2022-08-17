package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.query.HocKyQuery;
import com.fpolydatn.model.domain.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author vinhnv
 */
@Repository
public interface HocKyRepository extends JpaRepository<HocKy, UUID> {

    @Query(value = """
        SELECT id, ten_hoc_ky,created_date,last_modified_date 
            FROM hoc_ky hk 
            WHERE 
                ( :#{#request.tenHocKy} IS NULL 
                    OR :#{#request.tenHocKy} LIKE '' 
                    OR ten_hoc_ky LIKE %:#{#request.tenHocKy}% ) 
            ORDER BY last_modified_date DESC 
            LIMIT :#{#request.limit} OFFSET :#{#request.offset}
        """, nativeQuery = true)
    List<HocKy> findByName(@Param("request") HocKyQuery request);

    @Query(value = """
        SELECT COUNT(1) FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy%
        """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);

}
