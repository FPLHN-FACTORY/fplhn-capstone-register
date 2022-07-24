package com.fpolydatn.infrastructure.request;

import lombok.Data;

import java.util.UUID;

/**
 * @author nguyenvv4
 */
@Data
public class CreateCanBoRequest {

    private String tenCanBo;

    private String tenTaiKhoan;

    private String soDienThoai;

    private String emailFpt;

    private String emailFe;

    private UUID coSoId;

    private UUID vaiTroId;
}
