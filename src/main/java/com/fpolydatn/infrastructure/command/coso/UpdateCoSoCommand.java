package com.fpolydatn.infrastructure.command.coso;

import com.fpolydatn.infrastructure.command.UpdateCommand;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author caodinh
 */
@Getter
@Setter
public class UpdateCoSoCommand extends BaseCoSoCommand implements UpdateCommand {

    @NotEmpty
    @NotBlank
    private String id;

}
