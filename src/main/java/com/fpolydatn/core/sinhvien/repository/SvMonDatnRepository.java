package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author SonPT
 */
public interface SvMonDatnRepository extends MonDatnRepository {

    @Query(value = "SELECT id,ma_mon,ten_mon_datn FROM mon_datn", nativeQuery = true)
    List<SvMonDatnResponse> findMonDatn();

}
