package com.fpolydatn.infrastructure.request.chuyennganh;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class BaseChuyenNganhRequest {

    private String tenChuyenNganh;

    private String chuyenNganhMeId;

    private UUID coSoId;

}
