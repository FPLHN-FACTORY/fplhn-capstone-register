package com.fpolydatn.repository;

import com.fpolydatn.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(SinhVienRepository.NAME)
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {

    public static final String NAME = "BaseSinhVienRepository";
}
