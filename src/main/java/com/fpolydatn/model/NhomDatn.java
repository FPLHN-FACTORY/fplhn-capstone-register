package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
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
public class NhomDatn implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID nhomId;

    @Column(length = 100)
    @Nationalized
    private String tenNhom;

    @Column(length = 100)
    @Nationalized
    private String tenDeTai;

    @Column
    @Nationalized
    private String moTa;

    @Column(length = 50)
    private String matKhau;

    @Column(nullable = false)
    private TrangThaiNhom trangThai;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID truongNhomId;

    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID giangVienId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID dotDangKyId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID coSoId;

}
