package com.fpolydatn.repository;

import com.fpolydatn.entity.CanBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(CanBoRepository.NAME)
public interface CanBoRepository extends JpaRepository<CanBo, String> {

    public static final String NAME = "BaseCanBoRepository";
}