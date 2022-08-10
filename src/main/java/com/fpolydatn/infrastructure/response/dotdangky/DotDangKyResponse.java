package com.fpolydatn.infrastructure.response.dotdangky;

import com.fpolydatn.model.domain.CoSo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author thepvph20110
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DotDangKyResponse {

    private UUID dotDangKyId;

    private String tenDotDangKy;

    private Long ngayBatDau;

    private Long ngayKetThuc;

    private UUID hocKyId;

    private CoSo coSo;

}
