package com.fpolydatn.core.daotao.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author SonPT
 */

@Data
public class DtLoaiSinhVienKhongDatRequest {

    private MultipartFile fileLoaiSv;

    private String coSoId;

    private String dotDangKyId;
}
