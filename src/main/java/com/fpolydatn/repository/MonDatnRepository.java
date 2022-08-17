package com.fpolydatn.repository;

import com.fpolydatn.model.entity.MonDatnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface MonDatnRepository extends JpaRepository<MonDatnEntity, UUID> {
}
