package com.fpolydatn.repository;

import com.fpolydatn.model.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface CoSoRepository extends JpaRepository<CoSo , UUID> {
}
