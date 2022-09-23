package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hoangnt
 */
public interface SvNhomDatnRepository extends NhomDatnRepository {
    @Query(value = "SELECT b.id,ROW_NUMBER() OVER() AS Stt,ma_nhom,ten_nhom,ten_dot_dang_ky,ten_de_tai,ma_sinh_vien,ten_sinh_vien,ten_gvhd," +
            "IF(b.id = a.truong_nhom_id,'Trưởng nhóm','Thành viên') as chuc_vu,trang_thai,nhan_xet\n" +
            " FROM nhom_datn a join sinh_vien b on a.id = b.nhom_id join giang_vien_huong_dan c on c.id = a.giang_vien_id\n" +
            " join dot_dang_ky d on d.id = a.dot_dang_ky_id where  a.id = :nhomDatnId",nativeQuery = true)
    List<SvNhomDatnResponse> getNhomDatnById(@Param("nhomDatnId") String nhomDatnId);

    @Query(value = "SELECT truong_nhom_id FROM nhom_datn where id = :nhomDatnId and co_so_id = :coSoId",nativeQuery = true)
    String getIdTruongNhom (@Param("nhomDatnId") String nhomDatnId,@Param("coSoId") String coSoId);
}
