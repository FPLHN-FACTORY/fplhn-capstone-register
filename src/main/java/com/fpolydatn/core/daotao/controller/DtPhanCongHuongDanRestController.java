package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.core.common.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    // tìm kiếm giảng viên => phân công giảng viên
    @GetMapping("/search-add/{id}")
    public ResponseObject searchAddPhanCongHuongDan(final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        if (req.getDotDangKy() == null || req.getDotDangKy().trim().length() == 0) {
            req.setDotDangKy(commonHelper.getDotDangKyHienTai());
        }
        req.setCoSoId(coSoId);
        return new ResponseObject(phanCongHuongDanService.getPhanCongGiaoVien(coSoId, id, req));
    }

    // tìm kiếm giảng viên hướng dẫn tham gia hướng dẫn môn
    @GetMapping("/search-home/{id}")
    public ResponseObject searchHomePhanCongHuongDan(final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id, @RequestParam("page") int page) {
        String coSoId = session.getCoSoId();
        if (req.getDotDangKy() == null || req.getDotDangKy().trim().length() == 0) {
            req.setDotDangKy(commonHelper.getDotDangKyHienTai());
        }
        req.setCoSoId(coSoId);
        PageableObject<DtPhanCongHuongDanResponse> listPhanCong = phanCongHuongDanService.search(id, req, page);
        return new ResponseObject(listPhanCong);
    }

    @GetMapping("/phan-mon-add/{id}")
    public ResponseObject viewPhanCongHuongDanAdd(final DtFindPhanMonGiangVienHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoid = session.getCoSoId();
        PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> listPhanCong = phanCongHuongDanService.getPhanMonDatn(coSoid, id, req);
        return new ResponseObject(listPhanCong);
    }

    @GetMapping("/phan-mon/{id}")
    public ResponseObject viewPhanCongHuongDan(final DtFindPhanMonGiangVienHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoid = session.getCoSoId();

        PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> listPhanCong = phanCongHuongDanService.getPhanMonDatnList(coSoid, id, req);
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

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy
    ) {
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_PHAN_CONG_HUONG_DAN);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        List<LoggerObject> list = writerFileCSV.readFileCSV(loggerObject);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy) throws IOException {

        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_PHAN_CONG_HUONG_DAN);

        File file = new File(pathFile);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
