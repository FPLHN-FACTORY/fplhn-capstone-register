package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotRepository extends SinhVienRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
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
            where s.ten_sinh_vien like %:#{#req.tenSinhVien}% and 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} or  :#{#req.idChuyenNganh} is null or :#{#req.idChuyenNganh} like '')
            and (if('b'= :#{#req.idTrangThaiNhom},nhom_id is null,if('a'= :#{#req.idTrangThaiNhom},nhom_id is not null, s.ten_sinh_vien like %:#{#req.tenSinhVien}%)))
            and  (s.dot_dang_ky_id = :#{#req.idDotDangKy} or :#{#req.idDotDangKy} is null or :#{#req.idDotDangKy} like '' ) and s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id 
            ORDER BY s.last_modified_date DESC """ ,countQuery = """
             SELECT count(s.id) 
            FROM do_an_tot_nghiep.sinh_vien s 
            left join do_an_tot_nghiep.nhom_datn n on n.id= s.nhom_id 
            left join do_an_tot_nghiep.chuyen_nganh c on c.id= s.chuyen_nganh_id 
            left join do_an_tot_nghiep.mon_datn m on m.id = s.mon_datn_id 
            left join do_an_tot_nghiep.giang_vien_huong_dan g on g.id = n.giang_vien_id 
            left join do_an_tot_nghiep.dot_dang_ky d on d.id =s.dot_dang_ky_id 
            where s.ten_sinh_vien like %:#{#req.tenSinhVien}% and 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} or  :#{#req.idChuyenNganh} is null or :#{#req.idChuyenNganh} like '')
            and (if('b'= :#{#req.idTrangThaiNhom},nhom_id is null,if('a'= :#{#req.idTrangThaiNhom},nhom_id is not null, s.ten_sinh_vien like %:#{#req.tenSinhVien}%)))
            and  (s.dot_dang_ky_id = :#{#req.idDotDangKy} or :#{#req.idDotDangKy} is null or :#{#req.idDotDangKy} like '' ) and s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id 
            """, nativeQuery = true)
    Page<DtSinhVienTheoDotResponse> searchStudent(@Param("req") FindDtSinhVienTheoDotRequest req, Pageable page);

    @Query(value = """ 
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
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
            WHERE s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id 
            ORDER BY s.last_modified_date DESC """,
            countQuery = """
            SELECT count(s.id) 
            FROM do_an_tot_nghiep.sinh_vien s
            left join do_an_tot_nghiep.nhom_datn n on n.id= s.nhom_id 
            left join do_an_tot_nghiep.chuyen_nganh c on c.id= s.chuyen_nganh_id
            left join do_an_tot_nghiep.mon_datn m on m.id = s.mon_datn_id 
            left join do_an_tot_nghiep.giang_vien_huong_dan g on g.id = n.giang_vien_id 
            left join do_an_tot_nghiep.dot_dang_ky d on d.id =s.dot_dang_ky_id 
            WHERE s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id
            """,nativeQuery = true)
    Page<DtSinhVienTheoDotResponse> findAllSinhVien(Pageable page, @Param("req") final FindDtSinhVienTheoDotRequest req);

}
