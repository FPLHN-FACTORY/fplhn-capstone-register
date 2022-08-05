package com.fpolydatn.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
public class PhanCongHuongDanId implements Serializable {

    private UUID giangVienId;

    private UUID monDatnId;
}
