package com.fpolydatn.core.admin.service.daotao.repository;

import com.fpolydatn.core.admin.service.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.admin.service.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.entity.MonDatn;
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
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, m.id, m.ma_mon, m.ten_mon_datn, cb.ten_tai_khoan 
                FROM do_an_tot_nghiep.chuyen_nganh cn 
                JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id 
                JOIN mon_datn m ON m.chuyen_nganh_id = cn.id 
                WHERE m.co_so_id = :#{#req.coSoId} AND
                    ( :#{#req.tenMonDatn} IS NULL 
                        OR :#{#req.tenMonDatn} LIKE '' 
                        OR m.ten_mon_datn LIKE %:#{#req.tenMonDatn}% ) 
                ORDER BY m.last_modified_date DESC 
            """, countQuery = """
            SELECT COUNT(m.id)
                FROM do_an_tot_nghiep.chuyen_nganh cn 
                JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id 
                JOIN mon_datn m ON m.chuyen_nganh_id = cn.id 
                WHERE m.co_so_id = :#{#req.coSoId} AND
                    ( :#{#req.tenMonDatn} IS NULL 
                        OR :#{#req.tenMonDatn} LIKE '' 
                        OR m.ten_mon_datn LIKE %:#{#req.tenMonDatn}% ) 
                ORDER BY m.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtMonDatnResponse> findByName(@Param("req") DtFindMonDatnRequest req, Pageable page);

    MonDatn findByMaMon(String maMon);
}
