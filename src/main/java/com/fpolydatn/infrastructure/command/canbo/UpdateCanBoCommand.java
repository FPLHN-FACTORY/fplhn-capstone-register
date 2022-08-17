package com.fpolydatn.infrastructure.command.canbo;

import com.fpolydatn.infrastructure.command.UpdateCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
public class UpdateCanBoCommand extends BaseCanBoCommand implements UpdateCommand {

    private String id;

}
