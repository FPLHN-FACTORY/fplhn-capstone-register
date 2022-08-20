package com.fpolydatn.repository;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(CanBoRepository.NAME)
public interface CanBoRepository extends JpaRepository<CanBo, String> {

    public static final String NAME = "BaseCanBoRepository";

    @Query(value = """
            SELECT id, ten_can_bo AS 'name' FROM can_bo
            WHERE co_so_id = :coSoId and vai_tro = :vaiTro
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(@Param("coSoId") String coSoId,
                                                         @Param("vaiTro") int vaiTro);
}
