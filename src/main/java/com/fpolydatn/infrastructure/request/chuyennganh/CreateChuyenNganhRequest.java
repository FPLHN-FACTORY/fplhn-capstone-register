package com.fpolydatn.infrastructure.request.chuyennganh;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CreateChuyenNganhRequest {

    private String tenChuyenNganh;

    private String chuyenNganhMeId;

    private UUID coSoId;

}
