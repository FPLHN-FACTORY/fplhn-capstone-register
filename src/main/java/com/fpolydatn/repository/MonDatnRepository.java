package com.fpolydatn.repository;

import com.fpolydatn.model.domain.MonDatn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface MonDatnRepository extends JpaRepository<MonDatn, UUID> {
}
