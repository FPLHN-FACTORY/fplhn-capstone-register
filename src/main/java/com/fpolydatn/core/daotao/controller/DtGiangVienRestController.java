package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
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

import java.io.IOException;


/**
 * @author Vinhnv
 */
@RestController
@RequestMapping("/api/dao-tao/giang-vien-huong-dan")
public class DtGiangVienRestController extends BaseController {

    @Autowired
    private DtGiangVienHDService giangVienHDService;

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateGiangVienHDRequest cmd) {
        try {
            String coSoid = session.getCoSoId();
            cmd.setCoSoId(coSoid);
            return new ResponseObject(giangVienHDService.create(cmd));
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateGiangVienHDRequest cmd) {
        try {
            cmd.setId(id);
            return new ResponseObject(giangVienHDService.update(cmd));
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        try {
            return new ResponseObject(giangVienHDService.delete(id));
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindGiangVienHDRequest rep) {
        PageableObject<DtGiangVienHDResponse> listGiangVienHD = giangVienHDService.search(rep);
        return new ResponseObject(listGiangVienHD);
    }

    @PostMapping("/import")
    public void importEcel(@RequestParam("file") MultipartFile file) throws IOException {
        this.giangVienHDService.importExcel(file);
    }

}
