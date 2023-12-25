package com.fpolydatn.core.sinhvien.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Getter
@Setter
public class ThamGiaNhomDatnRequest {
    private String sinhVienId;
    private String nhomDatnId;
    private String monDatnId;
}
