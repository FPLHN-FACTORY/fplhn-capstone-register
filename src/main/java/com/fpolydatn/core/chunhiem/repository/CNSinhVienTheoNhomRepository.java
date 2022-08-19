package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.FindSinhVienRequest;
import com.fpolydatn.core.chunhiem.model.response.SinhVienTheoNhomResponse;
import com.fpolydatn.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface CNSinhVienTheoNhomRepository extends JpaRepository<SinhVien,String> {

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS STT, ma_sinh_vien,ten_sinh_vien,b.ten_chuyen_nganh,d.ten_dot_dang_ky,e.ten_nhom,f.ten_gvhd FROM sinh_vien a 
            JOIN chuyen_nganh b  ON a.chuyen_nganh_id = b.id 
            JOIN mon_datn c ON c.id = a.mon_datn_id 
            JOIN dot_dang_ky  d ON d.id = a.dot_dang_ky_id 
            JOIN nhom_datn e ON e.id= a.nhom_id 
            JOIN giang_vien_huong_dan  f ON f.id  = e.giang_vien_id 
            WHERE e.id = :idNhom ORDER BY ten_sinh_vien """, nativeQuery = true)
    Page<SinhVienTheoNhomResponse> searchSinhVien(@Param("idNhom") FindSinhVienRequest idNhom, Pageable page);

    @Query(value = "SELECT COUNT(1) FROM sinh_vien a WHERE a.nhom_id  LIKE %:id_nhom% ", nativeQuery = true)
    Long countListSinhVien(@Param("id_nhom")String id_nhom);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien AND nhom_id IS NULL",nativeQuery = true)
    String  getSinhVienByMaSinhVienandMaNhom(@Param("maSinhVien") String maSinhVien);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien LIKE :maSinhVien",nativeQuery = true)
    String getIdSinhVienByMaSinhVien(@Param("maSinhVien") String maSinhVien);

    @Query(value = "SELECT id FROM sinh_vien WHERE ma_sinh_vien = :maSinhVien and nhom_id is null",nativeQuery = true)
    String getIdbyMaSinhVienAndMaNhom(@Param("maSinhVien")String maSinhVien);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = NULL WHERE ma_sinh_vien = :maSinhVien",nativeQuery = true)
    void deleteSinhVienOutGroup(@Param("maSinhVien") String maSinhVien);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = :idNhom WHERE ma_sinh_vien = :maSinhVien",nativeQuery = true)
    void addSinhVienInGroup(@Param("idNhom") String idNhom, @Param("maSinhVien") String maSinhVien);


}
