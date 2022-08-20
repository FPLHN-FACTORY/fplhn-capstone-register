package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import com.fpolydatn.repository.ChuyenNganhRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DtChuyenNganhRepository extends ChuyenNganhRepository {

    @Query(value = "SELECT cn.id , ten_chuyen_nganh ,cs.ten_co_so,cb.ten_can_bo FROM chuyen_nganh " +
            "cn join co_so cs ON cn.co_so_id = cs.id join can_bo cb on cb.id = cn.chu_nhiem_bo_mon WHERE " +
            "( :#{#req.tenChuyenNganh} IS NULL \n" +
            "                    OR :#{#req.tenChuyenNganh} LIKE '' \n" +
            "                    OR ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}% ) ORDER BY cn.last_modified_date DESC",
            nativeQuery = true)
    Page<ChuyenNganhResponse> findByName(@Param("req")FindChuyenNganhRequest req, Pageable page);

    @Query(value = "SELECT COUNT(1) FROM chuyen_nganh cn WHERE ten_chuyen_nganh LIKE %tenChuyenNganh%",nativeQuery = true)
    Long countByName(@Param("tenChuyenNganh") String tenChuyenNganh);
}
