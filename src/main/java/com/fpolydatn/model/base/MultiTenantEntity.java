package com.fpolydatn.model.base;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
@MappedSuperclass
public abstract class MultiTenantEntity extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID coSoId;
}
