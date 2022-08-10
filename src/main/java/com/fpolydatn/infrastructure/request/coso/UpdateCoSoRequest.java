package com.fpolydatn.infrastructure.request.coso;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author caodinh
 */
@Getter
@Setter
public class UpdateCoSoRequest extends BaseCoSoRequest {

    private UUID id;

}
