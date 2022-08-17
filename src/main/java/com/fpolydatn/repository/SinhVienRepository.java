package com.fpolydatn.repository;

import com.fpolydatn.model.entity.SinhVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface SinhVienRepository extends JpaRepository<SinhVienEntity, UUID> {
}
