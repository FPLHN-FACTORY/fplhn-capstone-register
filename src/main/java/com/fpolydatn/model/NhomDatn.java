package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
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
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID nhomId;

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String tenNhom;

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String tenDeTai;

    @Column
    @Nationalized
    private String moTa;

    @Column(length = EntityProperties.LENGTH_PASSWORD)
    private String matKhau;

    @Column(nullable = false)
    private TrangThaiNhom trangThai;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID truongNhomId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID giangVienId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID dotDangKyId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID coSoId;

}
