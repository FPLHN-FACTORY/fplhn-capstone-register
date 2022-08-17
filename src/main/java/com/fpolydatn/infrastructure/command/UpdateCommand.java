package com.fpolydatn.infrastructure.command;

import java.util.UUID;

/**
 * @author phongtt35
 */
public interface UpdateCommand {

    UUID getId();
    void setId(UUID id);
}
