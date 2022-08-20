package com.fpolydatn.repository;

import com.fpolydatn.core.entity.NhomDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(NhomDatnRepository.NAME)
public interface NhomDatnRepository extends JpaRepository<NhomDatn, String> {

    public static final String NAME = "BaseNhomDatnRepository";
}
