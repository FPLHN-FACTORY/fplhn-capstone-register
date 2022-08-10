package com.fpolydatn.infrastructure.request.canbo;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
public class UpdateCanBoRequest {

    private String tenCanBo;

    private String tenTaiKhoan;

    private String soDienThoai;

    private String emailFpt;

    private String emailFe;

    private UUID coSoId;

    private UUID vaiTroId;

}
