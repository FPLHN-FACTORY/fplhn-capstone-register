package com.fpolydatn.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.entity.base.IsBelongToPeriod;
import com.fpolydatn.entity.base.MultiTenantEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "giang_vien_huong_dan")
@Entity
@ToString
public class GiangVienHuongDan extends MultiTenantEntity
        implements Serializable, IsBelongToPeriod {

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
    private String dotDangKyId;

}
