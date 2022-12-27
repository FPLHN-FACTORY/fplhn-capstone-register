package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author SonPT
 */
public interface SvMonDatnRepository extends MonDatnRepository {

    @Query(value = "SELECT nhom_mon_datn_id FROM do_an_tot_nghiep.mon_datn WHERE id = :id AND co_so_id = :coSoId", nativeQuery = true)
    String getNhomMonDatnIdById(@Param("id") String id, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT id,ma_mon,ten_mon_datn FROM mon_datn 
            WHERE nhom_mon_datn_id = :nhomMonId 
            AND co_so_id = :coSoId
            """, nativeQuery = true)
    List<SvMonDatnResponse> getMonDatnByNhomMonId(@Param("nhomMonId") String nhomMonId, @Param("coSoId") String coSoId);
}
