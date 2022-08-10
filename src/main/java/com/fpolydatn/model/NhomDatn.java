package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
@Table
@Entity
public class NhomDatn extends MultiTenantEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String maNhom;

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
}
