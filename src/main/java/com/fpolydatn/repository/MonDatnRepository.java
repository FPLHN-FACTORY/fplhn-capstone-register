package com.fpolydatn.repository;

import com.fpolydatn.entity.MonDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(MonDatnRepository.NAME)
public interface MonDatnRepository extends JpaRepository<MonDatn, String>
    , IMultiTenantRepository<MonDatn> {

    String NAME = "BaseMonDatnRepository";

//    @Query(value = """
//            SELECT id, ten_mon_datn AS 'name' FROM mon_datn
//            WHERE co_so_id = :coSoId
//                AND chuyen_nganh_id = :chuyenNganhId
//            """, nativeQuery = true)
//    List<SimpleEntityProj> findAllSimpleEntityByCoSoAndChuyenNganh(
//            @Param("coSoId") String coSoId, @Param("chuyenNganhId") String chuyenNganhId);
}
