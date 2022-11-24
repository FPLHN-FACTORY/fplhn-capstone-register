package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtNhomDatnTheoDotExport;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
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

@Service
public class DtNhomDatnTheoDotServiceImpl implements DtNhomDatnTheoDotService {
    @Autowired
    private DtNhomDatnTheoDotRepository dtNhomDatnTheoDotRepository;

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
    public List<DtSinhVienResponse> showDanhSachSinhVien(String idNhom, String coSoId, String dotDangKyId) {
        List<DtSinhVienResponse> res = dtNhomDatnTheoDotRepository.showDanhSachSinhVien(idNhom, coSoId, dotDangKyId);
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
}
