package com.fpolydatn.core.giangvien.repository;

import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author thangdt
 */


public interface GvNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER( ORDER BY nd.last_modified_date DESC ) AS stt, nd.id, nd.ten_nhom ,nd.ten_de_tai , cn.ten_chuyen_nganh,
                   COUNT(sv.ma_sinh_vien) AS total_ma_sinh_vien, c.ten_sinh_vien, c.ma_sinh_vien, gvhd.ten_gvhd, gvhd.ten_tai_khoan, nd.trang_thai
            FROM nhom_datn nd
            JOIN sinh_vien c ON nd.truong_nhom_id = c.id
            JOIN sinh_vien sv ON sv.nhom_id = nd.id
            JOIN chuyen_nganh cn ON cn.id = sv.chuyen_nganh_id
            JOIN giang_vien_huong_dan gvhd ON gvhd.id = nd.giang_vien_id
            JOIN dot_dang_ky ddk ON ddk.id = nd.dot_dang_ky_id
            JOIN hoc_ky hk on hk.id = ddk.hoc_ky_id
            WHERE   ( :#{#request.hocKyId} IS NULL\s
                          OR :#{#request.hocKyId} LIKE ''\s
                          OR hk.id LIKE %:#{#request.hocKyId}% )\s
                  AND 
                      ( :#{#request.chuyenNganhId} IS NULL\s
                          OR :#{#request.chuyenNganhId} LIKE ''\s
                          OR cn.id LIKE %:#{#request.chuyenNganhId}% )\s
                   AND                                              
                        ( :#{#request.dotDangKyId} IS NULL\s
                           OR :#{#request.dotDangKyId} LIKE ''\s
                           OR ddk.id LIKE %:#{#request.dotDangKyId}% )\s
            GROUP BY nd.id
            ORDER BY nd.last_modified_date DESC
            """,
            countQuery = """
             SELECT count(nd.id)
             FROM nhom_datn nd
            JOIN sinh_vien c ON nd.truong_nhom_id = c.id
            JOIN sinh_vien sv ON sv.nhom_id = nd.id
            JOIN chuyen_nganh cn ON cn.id = sv.chuyen_nganh_id
            JOIN giang_vien_huong_dan gvhd ON gvhd.id = nd.giang_vien_id
            JOIN dot_dang_ky ddk ON ddk.id = nd.dot_dang_ky_id
            JOIN hoc_ky hk on hk.id = ddk.hoc_ky_id
            WHERE   ( :#{#request.hocKyId} IS NULL
                          OR :#{#request.hocKyId} LIKE ''
                          OR hk.id LIKE %:#{#request.hocKyId}% )
                  AND 
                      ( :#{#request.chuyenNganhId} IS NULL
                          OR :#{#request.chuyenNganhId} LIKE ''
                          OR cn.id LIKE %:#{#request.chuyenNganhId}% )
                   AND                                              
                        ( :#{#request.dotDangKyId} IS NULL
                           OR :#{#request.dotDangKyId} LIKE ''
                           OR ddk.id LIKE %:#{#request.dotDangKyId}% )
            GROUP BY nd.id
            ORDER BY nd.last_modified_date DESC
            """, nativeQuery = true)
    Page<GvNhomDatnResponse> fillAllNhomDatn(@Param("request") GvFindNhomDatnRequest request, Pageable page);

}
