package com.fpolydatn.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * @author thepvph20110
 */

@Data
public class CreateDotDangKyRequest {

    private String tenDotDangKy;

    private String ngayBatDau;

    private String ngayKetThuc;

    private UUID hocKyId;

    private UUID coSoId;

}
