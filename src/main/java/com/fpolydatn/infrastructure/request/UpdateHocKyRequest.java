package com.fpolydatn.infrastructure.request;

import lombok.Data;

import java.util.UUID;

/**
 * @author huynq
 */
@Data
public class UpdateHocKyRequest {

    private UUID id;

    private String tenHocKy;

}
