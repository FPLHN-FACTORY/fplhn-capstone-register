package com.fpolydatn.repository;

import com.fpolydatn.core.entity.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(CoSoRepository.NAME)
public interface CoSoRepository extends JpaRepository<CoSo, String> {

    public static final String NAME = "BaseCoSoRepository";
}
