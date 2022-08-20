package com.fpolydatn.repository;

import com.fpolydatn.entity.GiangVienHuongDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(GiangVienHuongDanRepository.NAME)
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDan, String> {

    public static final String NAME = "BaseGiangVienHuongDanRepository";
}
