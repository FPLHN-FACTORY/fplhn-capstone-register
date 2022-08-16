package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.interactor.admin.SearchHocKyInteractor;
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
                ( :#{#interactor.tenHocKy} IS NULL 
                    OR :#{#interactor.tenHocKy} LIKE '' 
                    OR ten_hoc_ky LIKE %:#{#interactor.tenHocKy}% ) 
            ORDER BY last_modified_date DESC 
            LIMIT :#{#interactor.limit} OFFSET :#{#interactor.offset}
        """, nativeQuery = true)
    List<HocKy> findByName(@Param("interactor") SearchHocKyInteractor interactor);

    @Query(value = """
        SELECT COUNT(1) FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy%
        """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);

}
