package com.fpolydatn.repository;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(ChuyenNganhRepository.NAME)
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, String> {

    public static final String NAME = "BaseChuyenNganhRepository";

    @Query(value = """
            SELECT id, ten_chuyen_nganh AS 'name' FROM chuyen_nganh
            WHERE co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntityByCoSo(@Param("coSoId") String coSoId);
}
