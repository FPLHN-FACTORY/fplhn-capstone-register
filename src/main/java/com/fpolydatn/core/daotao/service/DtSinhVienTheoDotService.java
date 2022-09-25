package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotService {

    PageableObject<DtSinhVienTheoDotResponse> findAllSinhVien(final FindDtSinhVienTheoDotRequest request);

    PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request);

    boolean checkTypeFile(MultipartFile file);

    void DTSinhVienExport(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException;

    void DtSinhVienImport(MultipartFile file,String coSoId)throws IOException;
}
