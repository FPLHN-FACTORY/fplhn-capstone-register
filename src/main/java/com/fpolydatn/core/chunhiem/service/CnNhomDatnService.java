package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnNhomDatnService {

    PageableObject<CnNhomDatnResponse> search(final CnFindNhomDatnRequest req, String idChuNhiem);

    boolean chotDeTai(String id);

    boolean xacNhanNhom(String id);

    boolean tuChoiChotDeTai(String id, String nhanXet);

    Boolean delete(String id);

    NhomDatn findById(String id);

    boolean create(@Valid final CnCreateNhomDatnRequest cnCreateNhomDatnRequest);

    List<String> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem);

    void CnSinhVienExport(HttpServletResponse response, String typeExcel, CnFindNhomDatnRequest request) throws IOException;

    CnSinhVienImport CnSinhVienImport(MultipartFile file, String dotDangKyId, String coSoId, String idChuNhiem) throws IOException;

    boolean checkHanChuNhiem();

}