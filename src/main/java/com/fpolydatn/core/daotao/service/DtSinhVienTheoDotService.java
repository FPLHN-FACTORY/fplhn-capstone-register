package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtLoaiSinhVienKhongDatRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotService {

    PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request);

    boolean checkTypeFile(MultipartFile file);

    void DTSinhVienExport(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException;

    void MauImportExcel(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws Exception;

    DtLoaiSinhVienResponse DtSinhVienImport(MultipartFile file, String coSoId) throws IOException;

    DtLoaiSinhVienResponse loaiSinhVienKhongDat(DtLoaiSinhVienKhongDatRequest request);

    DtSinhVienTheoDotResponse searchStudentById(String id, String coSoId);
}
