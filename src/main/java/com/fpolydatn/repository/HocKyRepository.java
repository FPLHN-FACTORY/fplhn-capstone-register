package com.fpolydatn.repository;

import com.fpolydatn.entity.HocKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(HocKyRepository.NAME)
public interface HocKyRepository extends JpaRepository<HocKy, String> {

    public static final String NAME = "BaseHocKyRepository";

    @Query(value = """
            SELECT id, ten_hoc_ky AS 'name' FROM hoc_ky
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();

    @Query(value = """
        SELECT id FROM hoc_ky hk
        WHERE :currentTime BETWEEN hk.ngay_bat_dau AND hk.ngay_ket_thuc
        """, nativeQuery = true)
    String findHocKyId(@Param("currentTime") Long currentTime);
}
