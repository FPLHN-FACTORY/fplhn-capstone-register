package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

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
@Table
@Entity
public class SinhVien implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID sinhVienId;

    @Column(nullable = false)
    @Nationalized
    private String tenSinhVien;

    @Column(length = 10, nullable = false)
    private String maSinhVien;

    @Column(length = 20, nullable = false)
    private String soDienThoai;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String khoa;

    @Column
    private TrangThaiSinhVien trangThai;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID chuyenNganhId;

    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID nhomId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID coSoId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID dotDangKyId;
}
