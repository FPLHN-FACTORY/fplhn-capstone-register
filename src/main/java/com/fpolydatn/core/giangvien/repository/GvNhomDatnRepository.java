package com.fpolydatn.core.giangvien.repository;

import com.fpolydatn.core.giangvien.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.NhomDatnResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangdt
 */


public interface GvNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, nd.id, nd.ten_nhom ,nd.ten_de_tai , cn.ten_chuyen_nganh,
                   COUNT(sv.ma_sinh_vien) AS total_ma_sinh_vien, sv.ma_sinh_vien, gvhd.ten_gvhd, nd.trang_thai
            FROM nhom_datn nd
            JOIN sinh_vien sv ON sv.nhom_id = nd.id
            JOIN chuyen_nganh cn ON cn.id = sv.chuyen_nganh_id
            JOIN giang_vien_huong_dan gvhd ON gvhd.id = nd.giang_vien_id
            JOIN dot_dang_ky ddk ON ddk.id = nd.dot_dang_ky_id
            JOIN hoc_ky hk on hk.id = ddk.hoc_ky_id
            WHERE   ( :#{#request.tenHocKy} IS NULL\s
                          OR :#{#request.tenHocKy} LIKE ''\s
                          OR ten_hoc_ky LIKE %:#{#request.tenHocKy}% )\s
                  AND 
                      ( :#{#request.tenChuyenNganh} IS NULL\s
                          OR :#{#request.tenChuyenNganh} LIKE ''\s
                          OR ten_chuyen_nganh LIKE %:#{#request.tenChuyenNganh}% )\s
                   AND                                              
                        ( :#{#request.tenDotDangKy} IS NULL\s
                           OR :#{#request.tenDotDangKy} LIKE ''\s
                           OR ten_dot_dang_ky LIKE %:#{#request.tenDotDangKy}% )\s
            GROUP BY nd.id
            """, nativeQuery = true)
    Page<NhomDatnResponse> fillAllNhomDatn(@Param("request")  FindNhomDatnRequest request, Pageable page);

    @Query(value = """
            SELECT  ten_dot_dang_ky
            FROM dot_dang_ky
                        """, nativeQuery = true)
    List<String> getAllDotdangKy();

    @Query(value = """
            SELECT  ten_hoc_ky
            FROM hoc_ky
                        """, nativeQuery = true)
    List<String> getAllHocKy();

    @Query(value = """
           SELECT ten_chuyen_nganh
            FROM chuyen_nganh
                        """, nativeQuery = true)
    List<String> getAllChuyenNganh();
}
