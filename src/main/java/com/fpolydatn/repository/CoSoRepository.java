package com.fpolydatn.repository;

import com.fpolydatn.model.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface CoSoRepository extends JpaRepository<CoSo, UUID> {

    @Query(value = "Select co_so_id," +
            "ten_co_so" +
            " from co_so limit :size offset :offset", nativeQuery = true)
    List<CoSo> getListCoSo(@Param("offset") int offset, @Param("size") int size);

}
