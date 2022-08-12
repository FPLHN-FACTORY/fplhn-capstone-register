package com.fpolydatn.repository;

import com.fpolydatn.model.domain.GiangVienHuongDan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDan, UUID> {
}
