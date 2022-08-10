package com.fpolydatn.repository;

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
    @Query(value = "SELECT id, ten_hoc_ky FROM hoc_ky hk LIMIT :size OFFSET :offset", nativeQuery = true)
    List<HocKy>getListHocKy(@Param("offset") int offset, @Param("size") int size);
}
