package com.fpolydatn.repository;

import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.entity.PhanCongHuongDanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(PhanCongHuongDanRepository.NAME)
public interface PhanCongHuongDanRepository extends JpaRepository<PhanCongHuongDan, PhanCongHuongDanId> {

    public static final String NAME = "BasePhanCongHuongDanRepository";
}
