package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.chunhiem.service.CnMonDatnService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/nhom-datn")
public class CnNhomDatnRestController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnMonDatnService cnMonDatnService;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("/search")
    public ResponseObject viewNhomDatn(final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req, session.getUserId());
        return new ResponseObject(listNhomDatn);
    }

    @PostMapping
    public ResponseObject create(@RequestBody CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        cnCreateNhomDatnRequest.setCoSoId(session.getCoSoId());
        cnCreateNhomDatnRequest.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(nhomDatnService.create(cnCreateNhomDatnRequest));
    }

    @PutMapping("/chot-de-tai/{id}")
    public ResponseObject chotDeTai(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.chotDeTai(id));
    }

    @PutMapping("/xac-nhan-nhom/{id}")
    public ResponseObject xacNhanNhom(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.xacNhanNhom(id));
    }

    @PutMapping
    public ResponseObject tuChoiChotDeTai(@RequestParam("id") String id, @RequestParam("nhanXet") String nhanXet) {
        return new ResponseObject(nhomDatnService.tuChoiChotDeTai(id, nhanXet));
    }

    @GetMapping("/{id}")
    public ResponseObject detailNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.delete(id));
    }

    @GetMapping("/list-sinh-vien-no-group")
    public ResponseObject getlistNhomDatnNoGroup(final CnFindSinhVienNoGroupRequest req) {
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(nhomDatnService.getListSinhVienNoGroup(req, session.getUserId()));
    }

    @GetMapping("/list-mon-datn")
    public ResponseObject getAllMonDatnByNhomMon(@RequestParam("nhomMonId") String nhomMonId) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(cnMonDatnService.getAllMonDatnByNhomMon(coSoId, nhomMonId));
    }

    @GetMapping("/list-gvhd")
    public ResponseObject getAllGvhdByIdMon(@RequestParam("idMon") String idMon) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return new ResponseObject(cnGiangVienHuongDanService.getGiangVienHuongDanByIdMonDatn(idMon, coSoId, dotDangKyId));
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, CnFindNhomDatnRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        nhomDatnService.CnSinhVienExport(response, typeExcel, request);
    }

    @PostMapping("/import")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CnSinhVienImport response = nhomDatnService.CnSinhVienImport(file,dotDangKyId, coSoId, session.getUserId());
        return new ResponseObject(response);
    }

}
