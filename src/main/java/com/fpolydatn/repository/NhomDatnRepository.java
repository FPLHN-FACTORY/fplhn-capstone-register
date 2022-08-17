package com.fpolydatn.repository;

import com.fpolydatn.model.entity.NhomDatnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface NhomDatnRepository extends JpaRepository<NhomDatnEntity, UUID> {
}
