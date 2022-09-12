package com.fpolydatn.repository;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(MonDatnRepository.NAME)
public interface MonDatnRepository extends JpaRepository<MonDatn, String> {

    public static final String NAME = "BaseMonDatnRepository";

    @Query(value = """
            SELECT id, ten_mon_datn AS 'name' FROM mon_datn
            WHERE co_so_id = :coSoId
                AND chuyen_nganh_id = :chuyenNganhId
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntityByCoSoAndChuyenNganh(
            @Param("coSoId") String coSoId, @Param("chuyenNganhId") String chuyenNganhId);
}
