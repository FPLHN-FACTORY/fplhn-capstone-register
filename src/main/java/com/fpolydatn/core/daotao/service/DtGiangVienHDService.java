package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtGiagVienHDImportResquest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponseMessage;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponseMessage;
import com.fpolydatn.entity.GiangVienHuongDan;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author Vinhnv
 */
public interface DtGiangVienHDService {

    GiangVienHuongDan create(@Valid final DtCreateGiangVienHDRequest command);

    Boolean delete(final String id);

    GiangVienHuongDan update(@Valid final DtUpdateGiangVienHDRequest command);

    GiangVienHuongDan findById(final String id);

    long getSizeListHocKy(String tenGiangVien, String coSoId);

    PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest req);

    Object checkValidFile(List<DtGiagVienHDImportResquest> list, String coSoId);

    void DtGiangVienHDExportExcel(HttpServletResponse response, String typeExcel, final DtFindGiangVienHDRequest request) throws IOException;

    void DtGiangVienHDMauExportExcel(HttpServletResponse response, String typeExcel, final DtFindGiangVienHDRequest request) throws IOException;

     DtGiangVienHDResponseMessage DtGiangVienHDImport(MultipartFile file, String coSoId, String dotDangKyId);
}
