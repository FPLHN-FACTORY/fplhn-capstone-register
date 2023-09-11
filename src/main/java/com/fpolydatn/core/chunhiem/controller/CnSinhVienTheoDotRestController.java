package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotRestController extends BaseController {

    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("/search")
    public ResponseObject searchSinhVienTheoDot(final CnFindSinhVienTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setIdChuNhiem(session.getUserId());
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> listNhomDatnTheoDot = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        return new ResponseObject(listNhomDatnTheoDot);
    }

    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("id") String maNhom, @RequestParam("maSinhVien") String maSinhVien) {
        return new ResponseObject(cnSinhVienTheoDotService.addSinhVienTheoNhom(maNhom, maSinhVien));
    }

    @PostMapping("/random-nhom-datn")
    public ResponseObject randomNhomDatn(@RequestParam("number") Integer number) {
        String coSoId = session.getCoSoId();
        String idChuNhiem = session.getUserId();
        return new ResponseObject(cnSinhVienTheoDotService.randomNhomDatnByIdChuNhiem(idChuNhiem, coSoId, number));
    }

    @GetMapping("/export-sap-gvhd")
    public void exportExcelSapGvhd(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        cnSinhVienTheoDotService.CnExportSapGvhd(response, request, session.getUserId());
    }

    @PostMapping("/import-sap-gvhd")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CnNhomDatnImport response = cnSinhVienTheoDotService.importSapGvhd(file, dotDangKyId, coSoId, session.getUserId());
        return new ResponseObject(response);
    }
}
