package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/dao-tao/sinh-vien")
public class DtSinhVienTheoDotRestController extends BaseController {
    @Autowired
    private DtSinhVienTheoDotService sinhVienService;

    @GetMapping
    public ResponseObject searchStudent(final FindDtSinhVienTheoDotRequest req){
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtSinhVienTheoDotResponse> listSinhVien = sinhVienService.searchStudent(req);
        return new ResponseObject(listSinhVien);
    }

    @GetMapping("/export")
    public void exportEcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        sinhVienService.DTSinhVienExport(response,typeExcel,request);
    }
//    @PostMapping("/import")
//    public void exportEcel(@RequestParam("file")MultipartFile  file) throws IOException {
//        String coSoId =session.getCoSoId();
//       sinhVienService.DtSinhVienImport(file,coSoId);
//    }
}
