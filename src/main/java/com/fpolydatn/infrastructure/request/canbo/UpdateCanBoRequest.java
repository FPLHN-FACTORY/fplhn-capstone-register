package com.fpolydatn.infrastructure.request.canbo;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
public class UpdateCanBoRequest extends BaseCanBoRequest {

    private UUID id;
}
