package com.fpolydatn.repository;

import com.fpolydatn.model.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author vinhnv
 */

public interface HocKyRepository extends JpaRepository<HocKy, UUID> {
}
