package com.fpolydatn.repository;

import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(CoSoRepository.NAME)
public interface CoSoRepository extends JpaRepository<CoSo, String> {

    public static final String NAME = "BaseCoSoRepository";

    @Query(value = """
            SELECT id, ten_co_so AS 'name' FROM co_so
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();
}
