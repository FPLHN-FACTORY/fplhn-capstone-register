package com.fpolydatn.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.entity.base.HasPeriod;
import com.fpolydatn.entity.base.MultiTenantEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author phongtt35
 */
@Getter
@Setter
@Table(name = "dot_dang_ky")
@Entity
public class DotDangKy extends MultiTenantEntity
        implements Serializable, HasPeriod {

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenDotDangKy;

    @Column(nullable = false)
    private Long ngayBatDau;

    @Column(nullable = false)
    private Long ngayKetThuc;

    @Column(nullable = false)
    private Long hanSinhVien;

    @Column(nullable = false)
    private Long hanGiangVien;

    @Column(nullable = false)
    private Long hanChuNhiemBoMon;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String hocKyId;

}