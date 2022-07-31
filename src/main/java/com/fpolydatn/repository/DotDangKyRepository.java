package com.fpolydatn.repository;

import com.fpolydatn.model.DotDangKy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author thepvph20110
 */


public interface DotDangKyRepository extends JpaRepository<DotDangKy, UUID> {
}
