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
    @Query(value = "SELECT id, cn.co_so_id, ten_chuyen_nganh " +
            " FROM chuyen_nganh cn join co_so cs ON  cn.co_so_id = cs.co_so_id " +
            " WHERE cs.co_so_id = :id"+
            " LIMIT :size OFFSET :offset", nativeQuery = true)
    List<ChuyenNganh> getListChuyenNganh(@Param("id") UUID id,@Param("offset") int offset, @Param("size") int size);
}
