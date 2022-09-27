package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hoangnt
 */
public interface SvNhomDatnRepository extends NhomDatnRepository {

    @Query(value = "SELECT a.id, b.id AS id_sinh_vien, ROW_NUMBER() OVER() AS Stt, ma_nhom, ten_nhom, ten_dot_dang_ky, " +
            "ten_de_tai, ma_sinh_vien, ten_sinh_vien, ten_gvhd," +
            "IF(b.id = a.truong_nhom_id,'Trưởng nhóm','Thành viên') as chuc_vu, a.trang_thai, " +
            "IF(a.nhan_xet IS NULL, 'Không có nhận xét', a.nhan_xet) AS nhan_xet\n" +
            "FROM nhom_datn a join sinh_vien b on a.id = b.nhom_id " +
            "join giang_vien_huong_dan c on c.id = a.giang_vien_id\n" +
            "join dot_dang_ky d on d.id = a.dot_dang_ky_id where  a.id = :nhomDatnId",nativeQuery = true)
    List<SvDetailNhomDatnResponse> getNhomDatnById(@Param("nhomDatnId") String nhomDatnId);

    @Query(value = "SELECT id, co_so_id, dot_dang_ky_id, giang_vien_id, ma_nhom, " +
            "mat_khau, mo_ta, nhan_xet, ten_de_tai, ten_nhom, trang_thai, truong_nhom_id FROM nhom_datn " +
            "WHERE truong_nhom_id = :id", nativeQuery = true)
    NhomDatn getNhomDatnByTruongNhomId(@Param("id") String id);

    @Query(value = "SELECT truong_nhom_id FROM nhom_datn WHERE id = :nhomDatnId and co_so_id = :coSoId",nativeQuery = true)
    String getIdTruongNhom (@Param("nhomDatnId") String nhomDatnId,@Param("coSoId") String coSoId);

    @Query(value = "SELECT CONVERT(trang_thai , UNSIGNED) FROM nhom_datn WHERE id = :id", nativeQuery = true)
    int getTrangThai(@Param("id") String id);

    @Query(value = """
            SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ten_nhom
            ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien) AS total_ma_sinh_vien, c.ma_sinh_vien, c.ten_sinh_vien,
            e.ten_gvhd, e.ten_tai_khoan, b.trang_thai, b.last_modified_date, IF(b.mat_khau IS NULL, 0, 1) AS mat_khau_ton_tai
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%) 
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE ''
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%)
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId}
            AND g.id = :#{#req.dotDangKyId}
            AND b.trang_thai = 0
            GROUP BY b.id
            HAVING (:#{#req.soLuong} IS NULL OR :#{#req.soLuong} LIKE ''
            OR CONVERT(COUNT(d.ma_sinh_vien), UNSIGNED) = CONVERT(:#{#req.soLuong}, UNSIGNED))  AND COUNT(d.ma_sinh_vien) < 7          
            ORDER BY b.last_modified_date DESC
            """, countQuery = """
            SELECT count(b.id)
            FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
            JOIN sinh_vien d ON d.nhom_id = b.id
            JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
            JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
            JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
            JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
            WHERE (:#{#req.tenDeTai} IS NULL OR :#{#req.tenDeTai} LIKE ''
            OR b.ten_de_tai LIKE %:#{#req.tenDeTai}%) 
            AND (:#{#req.truongNhom} IS NULL OR :#{#req.truongNhom} LIKE ''
            OR c.ten_sinh_vien LIKE %:#{#req.truongNhom}%)
            AND (:#{#req.giangVien} IS NULL OR :#{#req.giangVien} LIKE ''
            OR e.ten_gvhd LIKE %:#{#req.giangVien}%) 
            AND b.co_so_id = :#{#req.coSoId}
            AND g.id = :#{#req.dotDangKyId}
            AND b.trang_thai = 0
            GROUP BY b.id
            HAVING (:#{#req.soLuong} IS NULL OR :#{#req.soLuong} LIKE ''
            OR CONVERT(COUNT(d.ma_sinh_vien), UNSIGNED) = CONVERT(:#{#req.soLuong}, UNSIGNED)) AND COUNT(d.ma_sinh_vien) < 7
            ORDER BY b.last_modified_date DESC
            """, nativeQuery = true)
    Page<SvNhomDatnResponse> findAllNhomDatn(Pageable page, @Param("req") SvFindNhomDatnRequest req);

    @Query(value = "SELECT id, ROW_NUMBER() OVER(ORDER BY last_modified_date DESC) AS stt, ma_sinh_vien, ten_sinh_vien " +
            "FROM sinh_vien \n" +
            "WHERE nhom_id = :id"
            , nativeQuery = true)
    List<SvShowSinhVienTheoNhomResponse> showDanhSachSinhVien(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :#{#req.idNhom} WHERE id = :#{#req.idUser}",nativeQuery = true)
    void addSinhVienInGroup(@Param("req") SvAddSinhVienRequest req);

    @Query(value = "SELECT mat_khau FROM nhom_datn WHERE id = :id", nativeQuery = true)
    String getMatKhauById(@Param("id") String id);

    @Query(value = "SELECT COUNT(id) FROM sinh_vien WHERE nhom_id = :nhomId GROUP BY nhom_id", nativeQuery = true)
    int getSoLuongThanhVienById(@Param("nhomId") String nhomId);

    @Query(value = "SELECT count(1) FROM nhom_datn n JOIN giang_vien_huong_dan g ON n.giang_vien_id = g.id " +
            "WHERE g.id LIKE :id", nativeQuery = true)
    Long countByGiangVienId(@Param("id") String id);

    @Query(value = "UPDATE sinh_vien SET nhom_id = NULL WHERE id = :id", nativeQuery = true)
    void roiNhom(@Param("id") String id);

}
