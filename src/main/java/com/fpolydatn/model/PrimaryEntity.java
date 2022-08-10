package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
@MappedSuperclass
public abstract class PrimaryEntity extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(length = EntityProperties.LENGTH_ID, updatable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID id;
}
