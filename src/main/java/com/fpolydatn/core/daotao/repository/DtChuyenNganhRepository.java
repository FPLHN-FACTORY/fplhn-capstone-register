package com.fpolydatn.core.daotao.repository;


import com.fpolydatn.repository.ChuyenNganhRepository;

/**
 * @author hungpv
 */

import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DtChuyenNganhRepository extends ChuyenNganhRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY cn.last_modified_date DESC) AS stt,cn.id,ten_chuyen_nganh,cs.ten_co_so,
            cb.ten_can_bo FROM chuyen_nganh cn JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id JOIN co_so cs ON 
            cn.co_so_id = cs.id WHERE cn.co_so_id = :#{#req.coSoId} and
                    ( :#{#req.tenChuyenNganh} IS NULL 
                        OR :#{#req.tenChuyenNganh} LIKE '' 
                        OR ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}% ) 
                ORDER BY cn.last_modified_date DESC 
            """, countQuery = """
            SELECT COUNT(cn.id)
                FROM chuyen_nganh cn JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id JOIN co_so cs ON 
            cn.co_so_id = cs.id WHERE cn.co_so_id = :#{#req.coSoId} and
                    ( :#{#req.tenChuyenNganh} IS NULL 
                        OR :#{#req.tenChuyenNganh} LIKE '' 
                        OR ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}% ) 
                ORDER BY cn.last_modified_date DESC
            """, nativeQuery = true)
    Page<ChuyenNganhResponse> findByName(@Param("req")FindChuyenNganhRequest req, Pageable page);

}
