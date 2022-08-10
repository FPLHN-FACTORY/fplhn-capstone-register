package com.fpolydatn.infrastructure.request.dotdangky;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author thepvph20110
 */

@Getter
@Setter
public class CreateDotDangKyRequest {

    private String tenDotDangKy;

    private String ngayBatDau;

    private String ngayKetThuc;

    private UUID hocKyId;

    private UUID coSoId;

}
