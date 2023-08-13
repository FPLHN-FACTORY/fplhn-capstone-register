package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huynq
 */
@RestController
@RequestMapping("/api/dao-tao/phan-cong-hong-dan")
public class DtPhanCongHuongDanRestController extends BaseController {

    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    // tìm kiếm giảng viên => phân công giảng viên
    @GetMapping("/search-add/{id}")
    public ResponseObject searchAddPhanCongHuongDan(final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        req.setDotDangKy(commonHelper.getDotDangKyHienTai());
        req.setCoSoId(coSoId);
        List<DtPhanCongHuongDanResponse> phanCongHuongDanGiaoViens = phanCongHuongDanService.getPhanCongGiaoVien(coSoId, id, req);
        return new ResponseObject(phanCongHuongDanGiaoViens);
    }

    // tìm kiếm giảng viên hướng dẫn tham gia hướng dẫn môn
    @GetMapping("/search-home/{id}")
    public ResponseObject searchHomePhanCongHuongDan(final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id, @RequestParam("page") int page) {
        String coSoId = session.getCoSoId();
        req.setDotDangKy(commonHelper.getDotDangKyHienTai());
        req.setCoSoId(coSoId);
        PageableObject<DtPhanCongHuongDanResponse> listPhanCong = phanCongHuongDanService.search(id, req, page);
        return new ResponseObject(listPhanCong);
    }

    @GetMapping("/phan-mon/{id}")
    public ResponseObject viewPhanCongHuongDan(final DtFindPhanMonGiangVienHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoid = session.getCoSoId();
        PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> listPhanCong = phanCongHuongDanService.getPhanMonDatn(coSoid, id, req);
        return new ResponseObject(listPhanCong);
    }

    @GetMapping()
    public ResponseObject detail(@RequestParam("idGiangVien") String idGiangVien, @RequestParam("idMon") String idMon) {
        return new ResponseObject(phanCongHuongDanService.findId(idGiangVien, idMon));
    }

    @DeleteMapping()
    public ResponseObject delete(@RequestParam("idGiangVien") String idGiangVien, @RequestParam("idMon") String idMon) {
        return new ResponseObject(phanCongHuongDanService.delete(idGiangVien, idMon));
    }

    @PostMapping()
    public ResponseObject addAll(@RequestBody List<PhanCongHuongDan> list) {
        return new ResponseObject(phanCongHuongDanService.addAll(list));
    }

}
