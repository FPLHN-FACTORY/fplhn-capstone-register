package com.fpolydatn.repository;

import com.fpolydatn.model.ChuyenNganh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, UUID> {

}
