package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
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
public class SinhVien extends MultiTenantEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenSinhVien;

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String maSinhVien;

    @Column(length = EntityProperties.LENGTH_PHONE, nullable = false)
    private String soDienThoai;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String email;

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String khoa;

    @Column
    private TrangThaiSinhVien trangThai;

    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID nhomId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID monDatnId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID chuyenNganhId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID dotDangKyId;
}
