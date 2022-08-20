package com.fpolydatn.entity.base;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author phongtt35
 */

@Getter
@Setter
@MappedSuperclass
public abstract class MultiTenantEntity extends PrimaryEntity implements IsMultiTenant {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String coSoId;

}
