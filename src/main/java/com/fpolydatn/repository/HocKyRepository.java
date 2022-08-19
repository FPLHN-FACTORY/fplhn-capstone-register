package com.fpolydatn.repository;

import com.fpolydatn.entity.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(HocKyRepository.NAME)
public interface HocKyRepository extends JpaRepository<HocKy, String> {

    public static final String NAME = "BaseHocKyRepository";
}
