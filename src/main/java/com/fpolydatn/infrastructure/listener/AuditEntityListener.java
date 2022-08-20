package com.fpolydatn.infrastructure.listener;

import com.fpolydatn.entity.base.AuditEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Calendar;

/**
 * @author phongtt35
 */
public class AuditEntityListener {

    @PrePersist
    private void onCreate(AuditEntity entity) {
        entity.setCreatedDate(getCurrentTime());
        entity.setLastModifiedDate(getCurrentTime());
    }

    @PreUpdate
    private void onUpdate(AuditEntity entity) {
        entity.setLastModifiedDate(getCurrentTime());
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
