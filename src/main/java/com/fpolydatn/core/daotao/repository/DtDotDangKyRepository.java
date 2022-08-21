package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author SonPT
 */

public interface DtDotDangKyRepository extends DotDangKyRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ddk.last_modified_date DESC) AS STT, ddk.id, hk.ten_hoc_ky, 
            ddk.ten_dot_dang_ky, ddk.ngay_bat_dau, ddk.ngay_ket_thuc, 
            COUNT(sv.dot_dang_ky_id) AS tong_sinh_vien, COUNT(sv.nhom_id) AS tong_sinh_vien_co_nhom, 
            FROM_UNIXTIME(ddk.ngay_ket_thuc / 1000) > CURRENT_DATE() AS trang_thai 
            FROM dot_dang_ky ddk LEFT JOIN sinh_vien sv ON ddk.id = sv.dot_dang_ky_id 
            INNER JOIN hoc_ky hk ON ddk.hoc_ky_id = hk.id WHERE ddk.co_so_id = :#{#req.coSoId} 
            GROUP BY ddk.id ORDER BY ddk.last_modified_date DESC
            """, nativeQuery = true)
    Page<DtDotDangKyResponse> findByCoSo(@Param("req") DtFindDotDangKyRequest requ, Pageable page);

}
