package com.fpolydatn.core.entity;

import com.fpolydatn.core.entity.base.IsBelongToPeriod;
import com.fpolydatn.core.entity.base.MultiTenantEntity;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
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
@Table(name = "nhom_datn")
@Entity
public class NhomDatn extends MultiTenantEntity
        implements Serializable, IsBelongToPeriod {

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
    private String truongNhomId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String giangVienId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String dotDangKyId;

}
