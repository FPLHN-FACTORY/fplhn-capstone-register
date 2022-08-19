package com.fpolydatn.repository;

import com.fpolydatn.entity.ChuyenNganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(ChuyenNganhRepository.NAME)
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, String> {

    public static final String NAME = "BaseChuyenNganhRepository";
}
