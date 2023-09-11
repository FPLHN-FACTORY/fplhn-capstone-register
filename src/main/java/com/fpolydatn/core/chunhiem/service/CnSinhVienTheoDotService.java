package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CnSinhVienTheoDotService {

    PageableObject<CnSinhVienTheoDotResponse> searchByIdDotDangKy(final CnFindSinhVienTheoDotRequest req);

    boolean addSinhVienTheoNhom(String maNhom,String maSinhVien);

    List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien,String coSoId);

    Boolean randomNhomDatnByIdChuNhiem(String idChuNhiem, String coSoId, Integer number);

    void CnExportSapGvhd(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request, String idChuNhiem) throws IOException;

    CnNhomDatnImport importSapGvhd(MultipartFile file, String dotDangKyId, String coSoId, String idChuNhiem) throws IOException;

}
