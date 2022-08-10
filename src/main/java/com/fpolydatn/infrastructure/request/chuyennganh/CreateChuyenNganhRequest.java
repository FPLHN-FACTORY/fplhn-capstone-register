package com.fpolydatn.infrastructure.request.chuyennganh;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

/**
 * @author thangncph26123
 */
@Data
public class CreateChuyenNganhRequest {

    private String tenChuyenNganh;

    private String chuyenNganhMeId;

    private UUID coSoId;

}
