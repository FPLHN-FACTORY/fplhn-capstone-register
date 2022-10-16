package com.fpolydatn.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
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
@Table(name = "mon_datn")
@Entity
public class MonDatn extends MultiTenantEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String maMon;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenMonDatn;

    @Column(length = EntityProperties.LENGTH_ID)
    private String nhomMonDatnId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String chuyenNganhId;
}
