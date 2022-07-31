package com.fpolydatn.infrastructure.response;

import com.fpolydatn.model.CoSo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private UUID hocKyId;

    private CoSo coSo;

}
