package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtNhomDatnExportExcel;
import com.fpolydatn.core.daotao.excel.DtNhomDatnTheoDotExport;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DtNhomDatnTheoDotServiceImpl implements DtNhomDatnTheoDotService {
    @Autowired
    private DtNhomDatnTheoDotRepository dtNhomDatnTheoDotRepository;

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienTheoDotRepository;

    @Autowired
    private CommonHelper commonHelper;

    private List<DtNhomDatnTheoDotResponse> listNhomDatn;

    @Override
    public PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(FindDtNhomDatnTheoDotRequest request) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getDotDangKyId() == null) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        } else if (request.getDotDangKyId().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtNhomDatnTheoDotResponse> res = dtNhomDatnTheoDotRepository.findAllNhomDatn(pageable, request);
        listNhomDatn = res.toList();
        return new PageableObject<>(res);
    }

    @Override
    public List<DtSinhVienResponse> showDanhSachSinhVien(String idNhom, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = dtNhomDatnTheoDotRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        List<DtSinhVienResponse> res = dtNhomDatnTheoDotRepository.showDanhSachSinhVien(idNhom, coSoId, nhomDatnFind.get().getDotDangKyId());
        return res;
    }

    @Override
    public void DTNhomDatnExport(HttpServletResponse response, String typeExcel, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        listNhomDatn = dtNhomDatnTheoDotRepository.findAllNhomDatnToExcel(request);
        DtNhomDatnTheoDotExport dtNhomDatnTheoDotExport = new DtNhomDatnTheoDotExport();
        dtNhomDatnTheoDotExport.exportData(response, listNhomDatn);
    }

    @Override
    public void DTNhomDatnSinhVienExport(HttpServletResponse response, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn.xlsx";
        response.setHeader(headerKey, headerValue);
        DtNhomDatnExportExcel mau = new DtNhomDatnExportExcel(sinhVienTheoDotRepository.getSinhVienNhomDatnExportExcel(request), sinhVienTheoDotRepository.getNhomDatnExportExcel(request));
        mau.exportData(response);
    }
}
