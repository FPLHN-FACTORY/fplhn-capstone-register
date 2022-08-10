package com.fpolydatn.infrastructure.request.dotdangky;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
public abstract class BaseDotDangKyRequest {

    private String tenDotDangKy;

    private String ngayBatDau;

    private String ngayKetThuc;

    private UUID hocKyId;

    private UUID coSoId;

}
