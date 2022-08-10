package com.fpolydatn.repository;

import com.fpolydatn.model.HocKy;
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

    @Query(value = "SELECT hoc_ky_id, ten_hoc_ky,create_at,update_at FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy% ORDER BY update_at DESC LIMIT :size OFFSET :offset", nativeQuery = true)
    List<HocKy> searchHocKy(@Param("tenHocKy") String tenHocKy, @Param("offset") int offset, @Param("size") int size);

    @Query(value = "SELECT COUNT(1) FROM hoc_ky hk WHERE ten_hoc_ky LIKE %:tenHocKy% ", nativeQuery = true)
    Long countListHocKy(@Param("tenHocKy") String tenHocKy);

}
