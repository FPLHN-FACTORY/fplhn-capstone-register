package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author hungpv
 */
public interface DtMonDatnRepository extends MonDatnRepository {
    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, m.id, m.ma_mon, m.ten_mon_datn, cb.ten_tai_khoan 
                FROM do_an_tot_nghiep.chuyen_nganh cn 
                JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id 
                JOIN mon_datn m on m.chuyen_nganh_id = cn.id 
                WHERE 
                    ( :#{#req.tenMon} IS NULL 
                        OR :#{#req.tenMon} LIKE '' 
                        OR ten_hoc_ky LIKE %:#{#req.tenMon}% ) 
                ORDER BY last_modified_date DESC 
            """, nativeQuery = true)
    Page<MonDatnResponse> findByName(@Param("req") FindMonDatnRequest req, Pageable page);

    @Query(value = """
            SELECT COUNT(1) FROM mon_datn m WHERE  LIKE %:tenHocKy%
            """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);
}
