package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Builder
@Table
@Entity
public class SinhVien implements Serializable {

    @Id
    @GeneratedValue
    private UUID sinhVienId;

    @Column(nullable = false)
    private String tenSinhVien;

    @Column(nullable = false)
    private String maSinhVien;

    @Column(nullable = false)
    private String soDienThoai;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String khoa;

    @Column
    private TrangThaiSinhVien trangThai;

    @Column(nullable = false)
    private UUID chuyenNganhId;

    @Column
    private UUID nhomId;

    @Column(nullable = false)
    private UUID coSoId;

    @Column(nullable = false)
    private UUID dotDangKyId;
}
