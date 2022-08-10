package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
@Table
@Entity
public class DotDangKy extends MultiTenantEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenDotDangKy;

    @Column(nullable = false)
    private Long ngayBatDau;

    @Column(nullable = false)
    private Long ngayKetThuc;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID hocKyId;
}
