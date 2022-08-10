package com.fpolydatn.infrastructure.request.coso;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author caodinh
 */
@Getter
@Setter
public class UpdateCoSoRequest extends BaseCoSoRequest {

    @NotEmpty
    @NotBlank
    private UUID id;

}
