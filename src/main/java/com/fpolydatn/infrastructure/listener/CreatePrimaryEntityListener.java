package com.fpolydatn.infrastructure.listener;

import com.fpolydatn.entity.base.PrimaryEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * @author phongtt35
 */
public class CreatePrimaryEntityListener {

    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setId(UUID.randomUUID().toString());
    }
}
