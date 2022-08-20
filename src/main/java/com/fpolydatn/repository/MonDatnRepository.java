package com.fpolydatn.repository;

import com.fpolydatn.core.entity.MonDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(MonDatnRepository.NAME)
public interface MonDatnRepository extends JpaRepository<MonDatn, String> {

    public static final String NAME = "BaseMonDatnRepository";
}
