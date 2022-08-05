package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

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
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID giangVienId;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenGvhd;

    @Column(nullable = false)
    private Short soNhomHuongDanToiDa;

    @Column(length = EntityProperties.LENGTH_ACCOUNT, nullable = false)
    private String tenTaiKhoan;

    @Column(length = EntityProperties.LENGTH_PHONE, nullable = false)
    private String soDienThoai;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String emailFpt;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String emailFe;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID dotDangKyId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID coSoId;
}
