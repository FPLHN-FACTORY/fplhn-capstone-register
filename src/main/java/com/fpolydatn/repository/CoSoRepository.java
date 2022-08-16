package com.fpolydatn.repository;

import com.fpolydatn.model.domain.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface CoSoRepository extends JpaRepository<CoSo, UUID> {

    @Query(value = "SELECT id,ma_co_so, ten_co_so,created_date,last_modified_date FROM co_so ", nativeQuery = true)
    List<CoSo> getListCoSo();

}
