package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtCanBoExportExcel;
import com.fpolydatn.core.daotao.excel.DtCanBoImportExcel;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoExcelImport;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponseMessage;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author nguyenvv4
 */
@Service
@Validated
public class DtCanBoServiceImpl implements DtCanBoService {

    @Autowired
    private DtCanBoRepository dtCanBoRepository;

    @Autowired
    private DtCoSoRepository dtCoSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Override
    public PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest) {
        Pageable pageable = PageRequest.of(canBoRequest.getPage(), canBoRequest.getSize());
        Page<DtCanBoResponse> res = dtCanBoRepository.findAllByTenCanBo(canBoRequest, pageable);
        return new PageableObject(res);
    }

    @Override
    @Transactional
    public boolean create(@Valid final DtCreateCanBoRequest canBoRequest) {
        String idByEmailFpt = dtCanBoRepository.getIdByEmailFpt(canBoRequest.getEmailFpt().trim(), canBoRequest.getCoSoId());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtCanBoRepository.getIdByEmailFe(canBoRequest.getEmailFe().trim(), canBoRequest.getCoSoId());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenTaiKhoan = dtCanBoRepository.getIdByTenTaiKhoan(canBoRequest.getTenTaiKhoan(), canBoRequest.getCoSoId());
        if (idByTenTaiKhoan != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_CAN_BO_EXIST);
        }
        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        dtCanBoRepository.save(canBo);
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Cán bộ '" + canBo.getTenTaiKhoan() + "' đã được thêm vào hệ thống.",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(String id, String coSoId) {
        Optional<CanBo> canBoCheck = dtCanBoRepository.findById(id);
        if (Objects.isNull(canBoCheck)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        Integer check = dtCanBoRepository.getIdChuyenNganhByIdChuNhiem(id, coSoId);
        if (check != null && check > 0) {
            throw new RestApiException(Message.CHU_NHIEM_NAY_DANG_CHU_NHIEM_1_CHUYEN_NGANH);
        }
        if (check == null || check == 0) {
            dtCanBoRepository.delete(canBoCheck.get());
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Cán bộ '" + canBoCheck.get().getTenTaiKhoan() + "' đã bị xóa khỏi hệ thống.",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public CanBo findById(String id) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(id);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        return canBo.get();
    }

    @Override
    @Transactional
    public CanBo update(@Valid DtUpdateCanBoRequest canBoRequest) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(canBoRequest.getId());
        String tenTkCu = canBo.get().getTenTaiKhoan();
        String tenCBCu = canBo.get().getTenCanBo();
        String sdtCu = canBo.get().getSoDienThoai();
        String mailFeCu = canBo.get().getEmailFe();
        String mailFptCu = canBo.get().getEmailFpt();
        VaiTro vaiTroCu = canBo.get().getVaiTro();
        String tenVaiTro = vaiTroCu.toString();

        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }

        Optional<CoSo> coSo = dtCoSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        String idByEmailFpt = dtCanBoRepository.getIdUpdateByEmailFpt(canBoRequest.getEmailFpt().trim(), canBo.get().getId(), canBoRequest.getCoSoId());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }

        String idByEmailFe = dtCanBoRepository.getIdUpdateByEmailFe(canBoRequest.getEmailFe().trim(), canBo.get().getId(), canBoRequest.getCoSoId());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }

        String idByTenTaiKhoan = dtCanBoRepository.getIdUpdateByTenTaiKhoan(canBoRequest.getTenTaiKhoan(), canBo.get().getId(), canBoRequest.getCoSoId());
        if (idByTenTaiKhoan != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_CAN_BO_EXIST);
        }

        CanBo updateCanBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        StringBuilder logMessage = new StringBuilder();

        logMessage.append(createLogMessageIfChanged("Tên tài khoản", tenTkCu, updateCanBo.getTenTaiKhoan()));
        logMessage.append(createLogMessageIfChanged("Tên cán bộ", tenCBCu, updateCanBo.getTenCanBo()));
        logMessage.append(createLogMessageIfChanged("Số điện thoại", sdtCu, updateCanBo.getSoDienThoai()));
        logMessage.append(createLogMessageIfChanged("Email FE", mailFeCu, updateCanBo.getEmailFe()));
        logMessage.append(createLogMessageIfChanged("Email FPT", mailFptCu, updateCanBo.getEmailFpt()));
        logMessage.append(createLogMessageIfChanged("Vai trò", tenVaiTro, updateCanBo.getVaiTro().toString()));

        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(logMessage.toString(),
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return dtCanBoRepository.save(updateCanBo);
    }

    private String createLogMessageIfChanged(String fieldName, String oldValue, String newValue) {
        if (!oldValue.equals(newValue)) {
            return String.format("Đào tạo đã sửa %s của cán bộ từ '%s' thành '%s'. ", fieldName, oldValue, newValue);
        }
        return "";
    }

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro) {
        return dtCanBoRepository.findCanBoByVaiTro(id, vaiTro);
    }


    @Override
    @Transactional
    public DtCanBoResponseMessage DtCanBoImport(MultipartFile file, String coSoId) throws IOException {
        DtCanBoResponseMessage responseMessage = new DtCanBoResponseMessage();
        ConcurrentMap<String, CanBo> canBos = new ConcurrentHashMap<>();

        try {
            List<DtCanBoExcelImport> importedCanBos = DtCanBoImportExcel.importData(file, coSoId);

            if (importedCanBos.isEmpty()) {
                responseMessage.setStatus(false);
                responseMessage.setMessage("Import không đúng định dạng của file excel mẫu");
                return responseMessage;
            }

            Map<String, String> existingData = getExistingData(coSoId);

            for (DtCanBoExcelImport canBo : importedCanBos) {
                if (isDataEmpty(canBo)) {
                    responseMessage.setStatus(false);
                    responseMessage.setMessage("Không được để trống");
                    return responseMessage;
                }

                if (isDuplicateData(existingData, canBo)) {
                    responseMessage.setStatus(false);
                    responseMessage.setMessage("Dữ liệu bị trùng");
                    return responseMessage;
                }

                if (!isValidVaiTro(canBo.getVaiTro())) {
                    responseMessage.setStatus(false);
                    responseMessage.setMessage("Vai trò không đúng định dạng");
                    return responseMessage;
                }

                CanBo newCanBo = createCanBo(canBo, coSoId);
                canBos.put(newCanBo.getTenTaiKhoan(), newCanBo);
            }

            if (responseMessage.getStatus()) {
                dtCanBoRepository.saveAll(canBos.values());
                logSuccessfulImport(canBos.values());
            }
        } catch (Exception e) {
            responseMessage.setStatus(false);
            responseMessage.setMessage("Lỗi hệ thống");
        }

        return responseMessage;
    }

    private Map<String, String> getExistingData(String coSoId) {
        Map<String, String> existingData = new HashMap<>();
        List<String> emailFes = dtCanBoRepository.findAllEmailFe(coSoId);
        List<String> emailFpts = dtCanBoRepository.findAllByEmailFPT(coSoId);
        List<String> tenTaiKhoans = dtCanBoRepository.finAllTenTaiKhoan(coSoId);

        addDataToList(existingData, emailFes);
        addDataToList(existingData, emailFpts);
        addDataToList(existingData, tenTaiKhoans);

        return existingData;
    }

    private void addDataToList(Map<String, String> data, List<String> list) {
        for (String item : list) {
            data.put(item, item);
        }
    }

    private boolean isDataEmpty(DtCanBoExcelImport canBo) {
        return canBo.getEmailFE().isEmpty() || canBo.getEmailFPT().isEmpty() ||
                canBo.getSoDienThoai().isEmpty() || canBo.getTenTaiKhoan().isEmpty() ||
                canBo.getTenCanBo().isEmpty() || canBo.getVaiTro().isEmpty();
    }

    private boolean isDuplicateData(Map<String, String> existingData, DtCanBoExcelImport canBo) {
        return existingData.containsKey(canBo.getEmailFE()) ||
                existingData.containsKey(canBo.getEmailFPT()) ||
                existingData.containsKey(canBo.getTenTaiKhoan());
    }

    private boolean isValidVaiTro(String vaiTro) {
        return "DAO_TAO".equals(vaiTro) || "CHU_NHIEM_BO_MON".equals(vaiTro) || "QUAN_TRI_VIEN".equals(vaiTro);
    }

    private CanBo createCanBo(DtCanBoExcelImport canBo, String coSoId) {
        CanBo newCanBo = new CanBo();
        newCanBo.setEmailFe(canBo.getEmailFE());
        newCanBo.setEmailFpt(canBo.getEmailFPT());
        newCanBo.setTenCanBo(canBo.getTenCanBo());
        newCanBo.setSoDienThoai(canBo.getSoDienThoai());
        newCanBo.setVaiTro(getVaiTro(canBo.getVaiTro()));
        newCanBo.setCoSoId(coSoId);
        newCanBo.setTenTaiKhoan(canBo.getTenTaiKhoan());
        return newCanBo;
    }

    private VaiTro getVaiTro(String vaiTro) {
        if ("DAO_TAO".equals(vaiTro)) {
            return VaiTro.DAO_TAO;
        } else if ("CHU_NHIEM_BO_MON".equals(vaiTro)) {
            return VaiTro.CHU_NHIEM_BO_MON;
        } else if ("QUAN_TRI_VIEN".equals(vaiTro)) {
            return VaiTro.QUAN_TRI_VIEN;
        }
        return null;
    }

    private void logSuccessfulImport(Collection<CanBo> canBos) {
        canBos.forEach(canBo -> {
            try {
                String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Cán bộ '" + canBo.getTenTaiKhoan() + "' đã được thêm vào hệ thống.",
                                pathFileLogActive, "Thành công");
                logService.sendLogMessage(loggerObjectActive);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void MauImportExcel(HttpServletResponse response, String typeExcel) throws IOException {
        DtCanBoExportExcel dtCanBoExportExcel = new DtCanBoExportExcel();
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauImportExcel" + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        dtCanBoExportExcel.exportData(response);
    }

//    public void addDataInMap(Map<String, String> mapAll, String coSoId) {
//        List<String> listEmailFE = dtCanBoRepository.findAllEmailFe(coSoId);
//        setDataInMap(mapAll, listEmailFE);
//
//        List<String> listEmailFpt = dtCanBoRepository.findAllByEmailFPT(coSoId);
//        setDataInMap(mapAll, listEmailFpt);
//
//        List<String> listTenTaiKhoan = dtCanBoRepository.finAllTenTaiKhoan(coSoId);
//        setDataInMap(mapAll, listTenTaiKhoan);
//    }

    private void setDataInMap(Map<String, String> mapSimple, List<String> listStr) {
        for (String s : listStr) {
            mapSimple.put(s, s);
        }
    }

}

