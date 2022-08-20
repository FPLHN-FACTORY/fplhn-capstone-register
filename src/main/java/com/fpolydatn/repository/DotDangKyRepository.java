package com.fpolydatn.repository;

import com.fpolydatn.entity.DotDangKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(DotDangKyRepository.NAME)
public interface DotDangKyRepository extends JpaRepository<DotDangKy, String> {

    public static final String NAME = "BaseDotDangKyRepository";
}
