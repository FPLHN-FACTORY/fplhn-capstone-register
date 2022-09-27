package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ToanNT
 */
@Repository
public interface SvGiangVienHuongDanRepository extends GiangVienHuongDanRepository {
    @Query(value = "SELECT g.id, g.ten_gvhd FROM giang_vien_huong_dan g " +
            "JOIN phan_cong_huong_dan p ON p.giang_vien_id = g.id\n" +
            "JOIN mon_datn m ON p.mon_datn_id = m.id WHERE m.id = :id"
            , nativeQuery = true)
    List<SvGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(@Param("id") String idMon);

}
