package com.fpolydatn.repository;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(DotDangKyRepository.NAME)
public interface DotDangKyRepository extends JpaRepository<DotDangKy, String> {

    String NAME = "BaseDotDangKyRepository";

    @Query(value = """
            SELECT id, ten_dot_dang_ky AS 'name' FROM dot_dang_ky
            WHERE co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity(@Param("coSoId") String coSoId);
}
