package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienRequest;
import com.fpolydatn.core.daotao.model.request.DtLoaiSinhVienKhongDatRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnSearchResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotService {

    PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request);

    boolean checkTypeFile(MultipartFile file);

    void DTSinhVienExport(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException;

    void MauImportExcel(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws Exception;

    DtLoaiSinhVienResponse DtSinhVienImport(MultipartFile file, String coSoId, String dotDangKyId) throws IOException;

    DtLoaiSinhVienResponse importSinhVienKhongDuDieukien(DtLoaiSinhVienKhongDatRequest request);

    DtSinhVienTheoDotResponse searchStudentById(String id);

    SinhVien loaiSinhVien(String id, String coSoId, String dotDangKyId);

    Boolean delete(final String id, String coSoId, String dotDangKyId);

    List<SimpleEntityProj> getMaMonChuongTrinh(String coSoId);

    List<NhomDatn> getListNhomMon(String coSoId);

    SinhVien update(@Valid final DtUpdateSinhVienTheoDotRequest command);

    SinhVien create(@Valid final DtCreateSinhVienRequest command);

    List<DtMonDatnSearchResponse> getAllMonDatnByChuyenNganhId(String chuyenNganhId, String coSoId);
}
