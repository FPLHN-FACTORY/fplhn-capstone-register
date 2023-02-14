package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtGiangVienFileImportExcel;
import com.fpolydatn.core.daotao.excel.DtGiangVienHuongDanExport;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtGiagVienHDImportResquest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponseMessage;
import com.fpolydatn.core.daotao.repository.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author Vinhnv
 */
@Service
@Validated
public class DtGiangVienHDServiceImpl implements DtGiangVienHDService {

    @Autowired
    private DtGiangVienHDRepository dtGiangVienHDRepository;

    @Autowired
    private DtMonDatnRepository dtMonDatnRepository;

    @Autowired
    private DtPhanCongHuongDanRepository dtPhanCongHuongDanRepository;

    private HashMap<String, String> map;

    @Autowired
    private CommonHelper commonHelper;

    private FormUtils formUtils = new FormUtils();

    @Override
    public GiangVienHuongDan create(@Valid DtCreateGiangVienHDRequest command) {
        String idByEmailFpt = dtGiangVienHDRepository.getIdByEmailFpt(command.getEmailFpt().trim(), command.getCoSoId(), command.getDotDangKyId());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtGiangVienHDRepository.getIdByEmailFe(command.getEmailFe(), command.getCoSoId(), command.getDotDangKyId());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenGiangVien = dtGiangVienHDRepository.getIdByTenTaiKhoan(command.getTenTaiKhoan(), command.getCoSoId(), command.getDotDangKyId());
        if (idByTenGiangVien != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan gvhd = formUtils.convertToObject(GiangVienHuongDan.class, command);
        return dtGiangVienHDRepository.save(gvhd);
    }

    @Override
    public Boolean delete(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        int countSoNhomDangHuongDan = dtGiangVienHDRepository.getCountSoNhomDangHuongDan(id);
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        if (countSoNhomDangHuongDan != 0) {
            throw new RestApiException(Message.GIANG_VIEN_DANG_CO_NHOM_HUONG_DAN);
        }
        dtGiangVienHDRepository.delete(gvhdCheck.get());
        return true;
    }

    @Override
    public GiangVienHuongDan update(DtUpdateGiangVienHDRequest command) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(command.getId());
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }

        String idByEmailFpt = dtGiangVienHDRepository.getIdUpdateByEmailFpt(command.getEmailFpt().trim(), command.getId(), command.getCoSoId(), command.getDotDangKyId());
        System.out.println(idByEmailFpt + "aaaaaaaaaaa");
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtGiangVienHDRepository.getIdUpdateByEmailFe(command.getEmailFe(), command.getId(), command.getCoSoId(), command.getDotDangKyId());
        System.out.println(idByEmailFe + "aaaaaaaaaaa");

        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenGiangVien = dtGiangVienHDRepository.getIdUpdateByTenTaiKhoan(command.getTenTaiKhoan(), command.getId(), command.getCoSoId(), command.getDotDangKyId());
        System.out.println(idByTenGiangVien + "aaaaaaaaaaa");
        if (idByTenGiangVien != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan giangVien = gvhdCheck.get();
        giangVien.setTenGvhd(command.getTenGvhd());
        giangVien.setSoNhomHuongDanToiDa(command.getSoNhomHuongDanToiDa());
        giangVien.setTenTaiKhoan(command.getTenTaiKhoan());
        giangVien.setSoDienThoai(command.getSoDienThoai());
        giangVien.setEmailFpt(command.getEmailFpt());
        giangVien.setEmailFe(command.getEmailFe());
        giangVien.setDotDangKyId(command.getDotDangKyId());
        return dtGiangVienHDRepository.save(giangVien);
    }

    @Override
    public GiangVienHuongDan findById(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        return gvhdCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenGiangVien, String coSoId) {
        return dtGiangVienHDRepository.countByName(tenGiangVien, coSoId);
    }

    @Override
    public PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest request) {
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
        Page<DtGiangVienHDResponse> res = dtGiangVienHDRepository.findByName(request, pageable);
        return new PageableObject<>(res);
    }

    @Override
    public List<GiangVienHuongDan> checkValidFile(List<DtGiagVienHDImportResquest> list, String coSoId) {
        List<GiangVienHuongDan> listGVHD = new ArrayList<>();
        Map<String, GiangVienHuongDan> mapGiangVien = new HashMap<>();
        if (list == null) {
            throw new RestApiException(Message.FILE_EMPTY);
        }
        for (DtGiagVienHDImportResquest gvhd : list) {
            GiangVienHuongDan giangVien = new GiangVienHuongDan();
            giangVien.setTenGvhd(gvhd.getTenGvhd());
            giangVien.setTenTaiKhoan(gvhd.getTenTaiKhoan());
            giangVien.setSoNhomHuongDanToiDa(gvhd.getSoNhomHuongDanToiDa());
            giangVien.setSoDienThoai(gvhd.getSoDienThoai());
            giangVien.setEmailFpt(gvhd.getEmailFpt());
            giangVien.setEmailFe(gvhd.getEmailFe());
            giangVien.setDotDangKyId(gvhd.getDotDangKyId());
            giangVien.setCoSoId(gvhd.getCoSoId());
            listGVHD.add(giangVien);
            mapGiangVien.put(giangVien.getTenTaiKhoan(), giangVien);
        }
        dtGiangVienHDRepository.saveAll(listGVHD);
        List<PhanCongHuongDan> listPhanCong = new ArrayList<>();
        list.parallelStream().forEach(gv -> {
            GiangVienHuongDan giangVienHuongDan = mapGiangVien.get(gv.getTenTaiKhoan());
            String array[] = new String[]{};
            array = gv.getMonHoc().split(",");
            for (int i = 0; i < array.length; i++) {
                PhanCongHuongDan phanCongHuongDan = new PhanCongHuongDan();
                MonDatn monDatn = dtMonDatnRepository.findByMaMon(array[i].trim(), coSoId);
                phanCongHuongDan.setGiangVienId(giangVienHuongDan.getId());
                if (monDatn != null) {
                    phanCongHuongDan.setMonDatnId(monDatn.getId());
                    listPhanCong.add(phanCongHuongDan);
                }

            }
        });
        dtPhanCongHuongDanRepository.saveAll(listPhanCong);
        return listGVHD;
    }

    @Override
    public void DtGiangVienHDExportExcel(HttpServletResponse response, String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=GiangVienHuongDan" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        List<DtGiangVienHDResponse> data = dtGiangVienHDRepository.exportExcel(request);
        DtGiangVienHuongDanExport dtGiangVienHuongDanExport = new DtGiangVienHuongDanExport();
        dtGiangVienHuongDanExport.employeeDetailReport(response, data);
    }

    @Override
    public void DtGiangVienHDMauExportExcel(HttpServletResponse response, String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=GiangVienHuongDan" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        DtGiangVienHuongDanExport dtGiangVienHuongDanExport = new DtGiangVienHuongDanExport();
        dtGiangVienHuongDanExport.exportMauImport(response);
    }

    @Override
    public DtGiangVienHDResponseMessage DtGiangVienHDImport(MultipartFile file, String coSoId, String doDangKyId) {
        DtGiangVienHDResponseMessage dtGiangVienHDResponseMessage = new DtGiangVienHDResponseMessage();
        ConcurrentMap<String, DtGiagVienHDImportResquest> listGiangVien = new ConcurrentHashMap<>();
        try {
            dtGiangVienHDResponseMessage.setStatus(true);
            DtGiangVienFileImportExcel dtGiangVienHDImport = new DtGiangVienFileImportExcel();
            List<DtGiagVienHDImportResquest> listGVHD = dtGiangVienHDImport.importData(file);
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            addDataInMap(map, coSoId, doDangKyId);

            listGVHD.parallelStream().forEach(gv -> {
                if (gv.getEmailFe().isEmpty() || gv.getEmailFpt().isEmpty() ||
                        gv.getTenTaiKhoan().isEmpty() ||
                        gv.getSoNhomHuongDanToiDa() == null || gv.getTenGvhd().isEmpty()) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Tên, emailFE, emailFpt, Tên tài khoản, Số nhóm hướng dẫn không được để trống");
                    return;
                }

                if (map.get(gv.getEmailFpt()) != null) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Email Fpt "+ gv.getEmailFpt() +" bị trùng");
                    return;
                }

                if (map.get(gv.getEmailFe()) != null) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Email Fe "+ gv.getEmailFe() +" bị trùng");
                    return;
                }

                if (map.get(gv.getTenTaiKhoan()) != null) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Tên tài khoản " + gv.getTenTaiKhoan() +" bị trùng");
                    return;
                }

//                if (!gv.getEmailFpt().matches("\\w+@fpt.edu.vn")) {
//                    dtGiangVienHDResponseMessage.setStatus(false);
//                    dtGiangVienHDResponseMessage.setMessage("Email fpt không đúng định dạng");
//                    return;
//                }
//
//                if (!gv.getEmailFe().matches("\\w+@fe.edu.vn")) {
//                    dtGiangVienHDResponseMessage.setStatus(false);
//                    dtGiangVienHDResponseMessage.setMessage("Email fe không đúng định dạng");
//                    return;
//                }

                if (gv.getEmailFpt().length() > 50) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Email fpt " + gv.getEmailFpt() +" quá 50 ký tự");
                    return;
                }

                if (gv.getEmailFe().length() > 50) {
                    dtGiangVienHDResponseMessage.setStatus(false);
                    dtGiangVienHDResponseMessage.setMessage("Email fe "+ gv.getEmailFe() +" quá 50 ký tự");
                    return;
                }

//                if (!gv.getSoDienThoai().matches(Constants.REGEX_PHONE_NUMBER)) {
//                    dtGiangVienHDResponseMessage.setStatus(false);
//                    dtGiangVienHDResponseMessage.setMessage("Số điện thoại không đúng định dạng");
//                    return;
//                }
                DtGiagVienHDImportResquest giangVien = new DtGiagVienHDImportResquest();
                giangVien.setEmailFe(gv.getEmailFe());
                giangVien.setEmailFpt(gv.getEmailFpt());
                giangVien.setTenTaiKhoan(gv.getTenTaiKhoan());
                if (gv.getSoDienThoai() == null) {
                    giangVien.setSoDienThoai("");
                } else {
                    giangVien.setSoDienThoai(gv.getSoDienThoai());
                }
                giangVien.setSoNhomHuongDanToiDa(gv.getSoNhomHuongDanToiDa());
                giangVien.setTenGvhd(gv.getTenGvhd());
                giangVien.setMonHoc(gv.getMonHoc());
                giangVien.setCoSoId(coSoId);
                giangVien.setDotDangKyId(doDangKyId);
                map.put(gv.getTenTaiKhoan(),gv.getTenTaiKhoan());
                map.put(gv.getEmailFpt(),gv.getTenTaiKhoan());
                map.put(gv.getEmailFe(),gv.getTenTaiKhoan());
                listGiangVien.put(giangVien.getEmailFpt(), giangVien);
            });
            if (dtGiangVienHDResponseMessage.getStatus() == true) {
                checkValidFile(listGiangVien.values().stream().toList(), coSoId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dtGiangVienHDResponseMessage.setStatus(false);
            dtGiangVienHDResponseMessage.setMessage("Lỗi File Import");
            return dtGiangVienHDResponseMessage;
        }
        return dtGiangVienHDResponseMessage;
    }

    public void addDataInMap(ConcurrentHashMap<String, String> mapAll, String coSoId, String dotDangKyId) {
        List<String> listEmailFe = dtGiangVienHDRepository.findAllEmailFe(coSoId, dotDangKyId);
        setDataInMap(mapAll, listEmailFe);

        List<String> listEmailFPT = dtGiangVienHDRepository.findAllEmailFpt(coSoId, dotDangKyId);
        setDataInMap(mapAll, listEmailFPT);

        List<String> listTenTaiKhoan = dtGiangVienHDRepository.findAllTenTaiKhoan(coSoId, dotDangKyId);
        setDataInMap(mapAll, listTenTaiKhoan);
    }

    public void setDataInMap(ConcurrentHashMap<String, String> mapSimple, List<String> listStr) {
        for (String s : listStr) {
            mapSimple.put(s, s);
        }
    }

}
