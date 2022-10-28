package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
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

    long getSizeListHocKy(String tenGiangVien);

    PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest req);

    List<GiangVienHuongDan> importExcel(MultipartFile file) throws IOException;

    Object checkValidFile(List<GiangVienHuongDan> list);

    void DtGiangVienHDExportExcel(HttpServletResponse response, String typeExcel, final DtFindGiangVienHDRequest request) throws IOException;

    void DtGiangVienHDMauExportExcel(HttpServletResponse response, String typeExcel, final DtFindGiangVienHDRequest request) throws IOException;
}
