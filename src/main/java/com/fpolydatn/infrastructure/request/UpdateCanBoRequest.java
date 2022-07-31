package com.fpolydatn.infrastructure.request;

import lombok.Data;

import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
public class UpdateCanBoRequest {

    private String tenCanBo;

    private String tenTaiKhoan;

    private String soDienThoai;

    private String emailFpt;

    private String emailFe;

    private UUID coSoId;

    private UUID vaiTroId;

}
