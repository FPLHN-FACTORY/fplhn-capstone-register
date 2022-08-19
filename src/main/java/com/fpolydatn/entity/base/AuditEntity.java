package com.fpolydatn.entity.base;

import com.fpolydatn.infrastructure.listener.AuditEntityListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author phongtt35
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AuditEntity {

    @Column(updatable = false)
    private long createdDate;

    @Column
    private long lastModifiedDate;

}
