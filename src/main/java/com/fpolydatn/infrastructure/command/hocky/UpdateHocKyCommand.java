package com.fpolydatn.infrastructure.command.hocky;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author HangNT
 */
@Getter
@Setter
public class UpdateHocKyCommand extends BaseHocKyCommand {

    private UUID id;
}
