package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
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
public class NhomDatn implements Serializable {

    @Id
    @GeneratedValue
    private UUID nhomId;

    @Column
    private String tenNhom;

    @Column
    private String tenDeTai;

    @Column
    private String moTa;

    @Column
    private String matKhau;

    @Column(nullable = false)
    private TrangThaiNhom trangThai;

    @Column(nullable = false)
    private UUID truongNhomId;

    @Column
    private UUID giangVienId;

    @Column(nullable = false)
    private UUID dotDangKyId;

    @Column(nullable = false)
    private UUID coSoId;

}
