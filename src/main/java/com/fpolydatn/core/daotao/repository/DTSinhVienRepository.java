package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDTSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DTSinhVienResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author thepvph20110
 */
public interface DTSinhVienRepository extends SinhVienRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt,
            s.id,
            s.ma_sinh_vien,
            s.ten_sinh_vien,
            c.ten_chuyen_nganh,
            m.ten_mon_datn,
            n.ma_nhom,
            n.ten_de_tai,
            g.ten_gvhd ,
            d.ten_dot_dang_ky 
            FROM do_an_tot_nghiep.sinh_vien s 
            left join do_an_tot_nghiep.nhom_datn n on n.id= s.nhom_id 
            left join do_an_tot_nghiep.chuyen_nganh c on c.id= s.chuyen_nganh_id 
            left join do_an_tot_nghiep.mon_datn m on m.id = s.mon_datn_id 
            left join do_an_tot_nghiep.giang_vien_huong_dan g on g.id = n.giang_vien_id 
            left join do_an_tot_nghiep.dot_dang_ky d on d.id =s.dot_dang_ky_id 
            where s.ten_sinh_vien like %:#{#req.getTenSinhVien}% and 
            (c.ten_chuyen_nganh = :#{#req.getTenChuyenNganh} or  :#{#req.getTenChuyenNganh} is null or :#{#req.getTenChuyenNganh} like '')
            and (if('b'= :#{#req.getTrangThaiNhom},nhom_id is null,if('a'= :#{#req.getTrangThaiNhom},nhom_id is not null, s.ten_sinh_vien like %:#{#req.getTenSinhVien}%)))
            and  (d.ten_dot_dang_ky = :#{#req.getTenDotDangKy} or :#{#req.getTenDotDangKy} is null or :#{#req.getTenDotDangKy} like '' ) and s.co_so_id = :#{#req.getCoSoId}
            GROUP BY s.id """ , nativeQuery = true)
    Page<DTSinhVienResponse> searchStudent(@Param("req")FindDTSinhVienRequest req, Pageable page);

    @Query(value = """ 
            SELECT ROW_NUMBER() OVER() AS stt,
            s.id,
            s.ma_sinh_vien,
            s.ten_sinh_vien,
            c.ten_chuyen_nganh,
            m.ten_mon_datn,
            n.ma_nhom,
            n.ten_de_tai,
            g.ten_gvhd ,
            d.ten_dot_dang_ky 
            FROM do_an_tot_nghiep.sinh_vien s
            left join do_an_tot_nghiep.nhom_datn n on n.id= s.nhom_id 
            left join do_an_tot_nghiep.chuyen_nganh c on c.id= s.chuyen_nganh_id
            left join do_an_tot_nghiep.mon_datn m on m.id = s.mon_datn_id 
            left join do_an_tot_nghiep.giang_vien_huong_dan g on g.id = n.giang_vien_id 
            left join do_an_tot_nghiep.dot_dang_ky d on d.id =s.dot_dang_ky_id 
            WHERE s.co_so_id = :#{#req.getCoSoId}
            GROUP BY s.id """, nativeQuery = true)
    Page<DTSinhVienResponse> findAllSinhVien(Pageable page,@Param("req") final FindDTSinhVienRequest req);

}
