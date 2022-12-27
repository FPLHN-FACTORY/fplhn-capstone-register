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
              SELECT ROW_NUMBER() OVER( ORDER BY nd.last_modified_date DESC ) AS stt, nd.ma_nhom, nd.id, nd.ten_de_tai , mdatn.ma_mon, 
                               COUNT(sv.ma_sinh_vien) AS total_ma_sinh_vien, nd.trang_thai
                        FROM nhom_datn nd
                         LEFT JOIN sinh_vien c ON nd.truong_nhom_id = c.id
                         LEFT JOIN sinh_vien sv ON sv.nhom_id = nd.id
                         LEFT JOIN chuyen_nganh cn ON cn.id = sv.chuyen_nganh_id
                         LEFT JOIN mon_datn mdatn ON mdatn.id = c.mon_datn_id
                         LEFT JOIN giang_vien_huong_dan gvhd ON gvhd.id = nd.giang_vien_id
                         LEFT JOIN dot_dang_ky ddk ON ddk.id = nd.dot_dang_ky_id
                         LEFT JOIN hoc_ky hk ON hk.id = ddk.hoc_ky_id
                 WHERE   gvhd.id LIKE :#{#request.giangVienHDId}
                 AND nd.co_so_id = :#{#request.coSoId}
                  AND ( :#{#request.monId} IS NULL
                          OR :#{#request.monId} LIKE ''
                          OR mdatn.id LIKE %:#{#request.monId}% )
                  AND UNIX_TIMESTAMP(localtime()) * 1000 BETWEEN ddk.ngay_bat_dau AND ddk.ngay_ket_thuc 
                  AND nd.trang_thai BETWEEN 1 AND 5
            GROUP BY nd.id
            ORDER BY nd.last_modified_date DESC
            """, countQuery = """
             SELECT count(nd.id)
             FROM nhom_datn nd
            FROM nhom_datn nd
                         LEFT JOIN sinh_vien c ON nd.truong_nhom_id = c.id
                         LEFT JOIN sinh_vien sv ON sv.nhom_id = nd.id
                         LEFT JOIN chuyen_nganh cn ON cn.id = sv.chuyen_nganh_id
                         LEFT JOIN mon_datn mdatn ON mdatn.id = c.mon_datn_id
                         LEFT JOIN giang_vien_huong_dan gvhd ON gvhd.id = nd.giang_vien_id
                         LEFT JOIN dot_dang_ky ddk ON ddk.id = nd.dot_dang_ky_id
                         LEFT JOIN hoc_ky hk ON hk.id = ddk.hoc_ky_id
            WHERE   gvhd.id LIKE :#{#request.giangVienHDId}
                    AND nd.co_so_id = :#{#request.coSoId}
                  AND ( :#{#request.monId} IS NULL
                          OR :#{#request.monId} LIKE ''
                          OR mdatn.id LIKE %:#{#request.monId}% )
                   AND UNIX_TIMESTAMP(localtime()) * 1000 BETWEEN ddk.ngay_bat_dau AND ddk.ngay_ket_thuc 
                  AND nd.trang_thai BETWEEN 1 AND 5
            GROUP BY nd.id
            ORDER BY nd.last_modified_date DESC
            """, nativeQuery = true)
    Page<GvNhomDatnResponse> fillAllNhomDatn(@Param("request") GvFindNhomDatnRequest request, Pageable page);

}
