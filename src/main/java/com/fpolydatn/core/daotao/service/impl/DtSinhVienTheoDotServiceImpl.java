package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotExport;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotImport;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author thepvph20110
 */
@Service
public class DtSinhVienTheoDotServiceImpl implements DtSinhVienTheoDotService {

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienRepository;

    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;

    private List<DtSinhVienTheoDotResponse> list;
    @Override
    public PageableObject<DtSinhVienTheoDotResponse> findAllSinhVien(final FindDtSinhVienTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Optional<SimpleEntityProj> dotDangEntityProj = dotDangKyRepository.DotDangKyHienTai();
        if(dotDangEntityProj.isPresent()){
            request.setIdDotDangKy(dotDangEntityProj.get().getId());
        }else {
            request.setIdDotDangKy("");
        }
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.findAllSinhVien(pageable,request);
        list = res.stream().toList();
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request) {
        Optional<SimpleEntityProj> dotDangEntityProj = dotDangKyRepository.DotDangKyHienTai();
        if(request.getIdDotDangKy().equalsIgnoreCase("")){
            if(dotDangEntityProj.isPresent()){
                request.setIdDotDangKy(dotDangEntityProj.get().getId());
            }else {
                request.setIdDotDangKy("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.searchStudent(request,pageable);
        list = res.stream().toList();
        return new PageableObject<>(res);
    }

    public boolean checkTypeFile(MultipartFile file) {
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

        String excel = "xls";
        if (!extension.equals(excel)){
            return false;
        }
        return true;
    }

    @Override
    public void DTSinhVienExport(HttpServletResponse response, String typeExcel,final FindDtSinhVienTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=QLSinhVienFpoly" + currentDateTime + "."+typeExcel;
        response.setHeader(headerKey, headerValue);
        DtSinhVienTheoDotExport dtSinhVienExport = new DtSinhVienTheoDotExport(list);
        dtSinhVienExport.exportData(response);
    }

    @Override
    public void DtSinhVienImport(MultipartFile file,String coSoId)throws IOException {
        DtSinhVienTheoDotImport sinhVienTheoDotImport = new DtSinhVienTheoDotImport();
        List<SinhVien> sinhVienList =  sinhVienTheoDotImport.importData(file);
        for (SinhVien sinhVien : sinhVienList){
            sinhVien.setCoSoId(coSoId);
            sinhVienRepository.save(sinhVien);
        }
    }

}
