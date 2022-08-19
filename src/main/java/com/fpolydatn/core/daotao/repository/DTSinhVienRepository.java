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
            where s.ten_sinh_vien like %:#{#rep.getTenSinhVien}% and 
            (c.ten_chuyen_nganh = :#{#rep.getTenChuyenNganh} or  :#{#rep.getTenChuyenNganh} is null or :#{#rep.getTenChuyenNganh} like '')
            and (if('b'= :#{#rep.getTrangThaiNhom},nhom_id is null,if('a'= :#{#rep.getTrangThaiNhom},nhom_id is not null, s.ten_sinh_vien like %:#{#rep.getTenSinhVien}%)))
            and  (d.ten_dot_dang_ky = :#{#rep.getTenDotDangKy} or :#{#rep.getTenDotDangKy} is null or :#{#rep.getTenDotDangKy} like '' )
            GROUP BY s.id """ , nativeQuery = true)
    Page<DTSinhVienResponse> searchStudent(@Param("rep")FindDTSinhVienRequest rep, Pageable page);

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
            GROUP BY s.id """, nativeQuery = true)
    Page<DTSinhVienResponse> findAllSinhVien(Pageable page);

    @Query(value = "SELECT COUNT(1) FROM do_an_tot_nghiep.sinh_vien s " +
            "left join do_an_tot_nghiep.nhom_datn n on n.id= s.nhom_id " +
            "left join do_an_tot_nghiep.chuyen_nganh c on c.id= s.chuyen_nganh_id " +
            "left join do_an_tot_nghiep.mon_datn m on m.id = s.mon_datn_id " +
            "left join do_an_tot_nghiep.giang_vien_huong_dan g on g.id = n.giang_vien_id ", nativeQuery = true)
    Long countListSinhVien();
}
