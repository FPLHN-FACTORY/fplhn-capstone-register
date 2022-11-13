package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author caodinh
 */
public interface DtCanBoRepository extends CanBoRepository {

    @Query(value = """
            SELECT  ROW_NUMBER() OVER(ORDER BY cb.last_modified_date DESC) AS STT, cb.id,cb.email_fe,
                    cb.email_fpt,cb.so_dien_thoai,cb.last_modified_date,
                    cb.ten_can_bo,cb.ten_tai_khoan ,cb.vai_tro,
                    cn.ten_chuyen_nganh
                    FROM do_an_tot_nghiep.can_bo cb
                    JOIN do_an_tot_nghiep.co_so cs
                    ON cb.co_so_id= cs.id 
                    LEFT JOIN do_an_tot_nghiep.chuyen_nganh cn on cb.id = cn.chu_nhiem_bo_mon
                     WHERE cb.co_so_id = :#{#req.coSoID}
            ORDER BY cb.last_modified_date DESC
             """,
            countQuery = """
                    SELECT  COUNT(cb.id)
                            FROM do_an_tot_nghiep.can_bo cb 
                            JOIN do_an_tot_nghiep.co_so cs
                            ON cb.co_so_id= cs.id  WHERE cb.co_so_id = :#{#req.coSoID}
                            ORDER BY cb.last_modified_date DESC 
                    """,
            nativeQuery = true)
    Page<DtCanBoResponse> findAllByTenCanBo(@Param("req") DtFindCanBoRequest canBoRequest, Pageable page);

    @Query(value = """
            SELECT id, ten_can_bo AS 'name' FROM can_bo a            
            WHERE co_so_id = :coSoId AND vai_tro = :vaiTro 
            AND (SELECT b.chu_nhiem_bo_mon FROM chuyen_nganh b WHERE b.chu_nhiem_bo_mon = a.id) IS NULL
            """, nativeQuery = true)
    List<SimpleEntityProj> findCanBoByVaiTro(@Param("coSoId") String coSoId, @Param("vaiTro") int vaiTro);

    @Query(value = """
            SELECT email_fe FROM can_bo""", nativeQuery = true)
    List<String> findAllEmailFe();

    @Query(value = """
            SELECT email_fpt FROM can_bo""", nativeQuery = true)
    List<String> findAllByEmailFPT();

    @Query(value = "SELECT id FROM can_bo WHERE email_fpt = :emailFpt", nativeQuery = true)
    String getIdByEmailFpt(@Param("emailFpt") String emailFpt);

    @Query(value = "SELECT id FROM can_bo WHERE email_fpt = :emailFpt AND id <> :id", nativeQuery = true)
    String getIdUpdateByEmailFpt(@Param("emailFpt") String emailFpt, @Param("id") String id);

    @Query(value = "SELECT id FROM can_bo WHERE email_fe = :emailFe", nativeQuery = true)
    String getIdByEmailFe(@Param("emailFe") String emailFe);

    @Query(value = "SELECT id FROM can_bo WHERE email_fe = :emailFe AND id <> :id", nativeQuery = true)
    String getIdUpdateByEmailFe(@Param("emailFe") String emailFe, @Param("id") String id);

    @Query(value = "SELECT id FROM can_bo WHERE ten_tai_khoan = :tenTaiKhoan", nativeQuery = true)
    String getIdByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan);

    @Query(value = "SELECT id FROM can_bo WHERE ten_tai_khoan = :tenTaiKhoan AND id <> :id", nativeQuery = true)
    String getIdUpdateByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("id") String id);

}
