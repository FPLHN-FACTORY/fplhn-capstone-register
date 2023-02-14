package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponseMessage;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
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
 * @author Vinhnv
 */
@RestController
@RequestMapping("/api/dao-tao/giang-vien-huong-dan")
public class DtGiangVienRestController extends BaseController {

    @Autowired
    private DtGiangVienHDService giangVienHDService;

    @Autowired
    private CommonHelper commonHelper;

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateGiangVienHDRequest cmd) {
        String coSoid = session.getCoSoId();
        cmd.setCoSoId(coSoid);
        if (cmd.getDotDangKyId().equalsIgnoreCase("")) {
            cmd.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        }
        return new ResponseObject(giangVienHDService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateGiangVienHDRequest cmd) {
        cmd.setId(id);
        cmd.setCoSoId(session.getCoSoId());
        return new ResponseObject(giangVienHDService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.delete(id));
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindGiangVienHDRequest req) {
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtGiangVienHDResponse> listGiangVienHD = giangVienHDService.search(req);
        return new ResponseObject(listGiangVienHD);
    }

    @PostMapping("/import/{dotDangKyId}")
    public ResponseObject importEcel(@RequestParam("file") MultipartFile file, @PathVariable("dotDangKyId") String dotDangKyId) throws IOException {
        String idCoSo = session.getCoSoId();
        DtGiangVienHDResponseMessage responseMessage = giangVienHDService.DtGiangVienHDImport(file, idCoSo, dotDangKyId.trim());
        return new ResponseObject(responseMessage);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        giangVienHDService.DtGiangVienHDExportExcel(response, typeExcel, request);
    }

    @GetMapping("/export/mau")
    public void exportMauExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        giangVienHDService.DtGiangVienHDMauExportExcel(response, typeExcel, request);
    }
}
