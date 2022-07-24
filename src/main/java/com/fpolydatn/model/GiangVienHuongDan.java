package com.fpolydatn.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Table
@Entity
public class GiangVienHuongDan implements Serializable {

    @Id
    @GeneratedValue
    private UUID giangVienId;

    @Column(nullable = false)
    private String tenGvhd;

    @Column(nullable = false)
    private Short soNhomHuongDanToiDa;

    @Column(nullable = false)
    private String tenTaiKhoan;

    @Column(nullable = false)
    private String soDienThoai;

    @Column(nullable = false)
    private String emailFpt;

    @Column(nullable = false)
    private String emailFe;

    @Column(nullable = false)
    private UUID dotDangKyId;

    @Column(nullable = false)
    private UUID coSoId;
}
