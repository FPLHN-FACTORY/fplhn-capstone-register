package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.entity.HocKyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author vinhnv
 */
@Repository
public interface HocKyRepository extends JpaRepository<HocKyEntity, String> {

    @Query(value = """
        SELECT id, ten_hoc_ky, last_modified_date 
            FROM hoc_ky hk 
            WHERE 
                ( :#{#query.tenHocKy} IS NULL 
                    OR :#{#query.tenHocKy} LIKE '' 
                    OR ten_hoc_ky LIKE %:#{#query.tenHocKy}% ) 
            ORDER BY last_modified_date DESC 
            LIMIT :#{#query.limit} OFFSET :#{#query.offset}
        """, nativeQuery = true)
    List<HocKy> findByName(@Param("query") HocKyQuery query);

    @Query(value = """
        SELECT COUNT(1) FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy%
        """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);

}
