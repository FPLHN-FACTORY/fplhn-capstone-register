package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.entity.NhomDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CnNhomDatnRepository extends JpaRepository<NhomDatn,String> {

    @Query(value = "SELECT id FROM nhom_datn WHERE ma_nhom = :maNhom",nativeQuery = true)
    String getIdByMaNhom(@Param("maNhom") String maNhom);

    @Query(value = """
            SELECT count(a.ma_sinh_vien) as so_luong FROM sinh_vien a
            WHERE nhom_id = :idNhom""",nativeQuery = true)
    int getSoLuongThanhVienNhom(@Param("idNhom")String idNhom);
}
