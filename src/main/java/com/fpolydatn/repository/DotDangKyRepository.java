package com.fpolydatn.repository;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(DotDangKyRepository.NAME)
public interface DotDangKyRepository extends JpaRepository<DotDangKy, String> {

    public static final String NAME = "BaseDotDangKyRepository";

    @Query(value = """
            SELECT id, ten_dot_dang_ky AS 'name' FROM hoc_ky
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();
}
