package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CnMonDatnRepository extends MonDatnRepository {

    @Query(value = "SELECT nhom_mon_datn_id FROM mon_datn WHERE id = :idMaMon", nativeQuery = true)
    String  findIdNhomMonDatn(@Param("idMaMon")String id);

    @Query(value = "SELECT nhom_mon_datn_id from mon_datn where nhom_mon_datn_id = :idNhomMonDatn and id = :idMonChuongTrinh",nativeQuery = true)
    String checkMonChuongTrinh(@Param("idNhomMonDatn")String idNhomMonDatn,@Param("idMonChuongTrinh")String idMonChuongTrinh);

}
