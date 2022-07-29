package com.fpolydatn.repository;

import com.fpolydatn.model.ChuyenNganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Vinhnv
 */
@Repository
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, UUID> {

    @Modifying
    @Transactional
    @Query(value = "SELECT chuyen_nganh_id,\n" +
            "       chuyen_nganh_me_id,\n" +
            "       cn.co_so_id,\n" +
            "       ten_chuyen_nganh\n" +
            "FROM chuyen_nganh cn join co_so cs on  cn.co_so_id = cs.co_so_id " +
            "WHERE cs.co_so_id = :id"+
            "limit :size offset :offset", nativeQuery = true)
    List<ChuyenNganh> getListChuyenNganh(@Param("id") UUID id,@Param("offset") int offset, @Param("size") int size);
}
