package com.fpolydatn.model.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.model.base.MultiTenantEntity;
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
@Table(name = "chuyen_nganh")
@Entity
public class ChuyenNganhEntity extends MultiTenantEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenChuyenNganh;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String chuNhiemBoMon;

}
