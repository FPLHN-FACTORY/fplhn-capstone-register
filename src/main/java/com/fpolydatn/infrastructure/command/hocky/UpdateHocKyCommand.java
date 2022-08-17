package com.fpolydatn.infrastructure.command.hocky;

import com.fpolydatn.infrastructure.command.UpdateCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author HangNT
 */
@Getter
@Setter
public class UpdateHocKyCommand extends BaseHocKyCommand implements UpdateCommand {

    private UUID id;
}
