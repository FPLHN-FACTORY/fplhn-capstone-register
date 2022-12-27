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
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
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
import java.util.ArrayList;
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

    @Override
    public PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest) {
        Pageable pageable = PageRequest.of(canBoRequest.getPage(), canBoRequest.getSize());
        Page<DtCanBoResponse> res = dtCanBoRepository.findAllByTenCanBo(canBoRequest, pageable);
        return new PageableObject(res);
    }

    @Override
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
        return true;
    }

    @Override
    public Boolean delete(String id, String coSoId) {
        Optional<CanBo> canBoCheck = dtCanBoRepository.findById(id);
        if (Objects.isNull(canBoCheck)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        String check = dtCanBoRepository.getIdChuyenNganhByIdChuNhiem(id, coSoId);
        if (check != null) {
            throw new RestApiException(Message.CHU_NHIEM_NAY_DANG_CHU_NHIEM_1_CHUYEN_NGANH);
        }
        dtCanBoRepository.delete(canBoCheck.get());
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
    public CanBo update(DtUpdateCanBoRequest canBoRequest) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(canBoRequest.getId());
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
        return dtCanBoRepository.save(updateCanBo);
    }

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro) {
        return dtCanBoRepository.findCanBoByVaiTro(id, vaiTro);
    }


    @Override
    public DtCanBoResponseMessage DtCanBoImport(MultipartFile file, String coSoId) throws IOException {
        DtCanBoResponseMessage dtCanBoResponseMessage = new DtCanBoResponseMessage();
        ConcurrentMap<String, CanBo> listCanBo = new ConcurrentHashMap<>();
        try {
            dtCanBoResponseMessage.setStatus(true);
            DtCanBoImportExcel dtCanBoImportExcel = new DtCanBoImportExcel();
            List<DtCanBoExcelImport> listCB = dtCanBoImportExcel.importData(file, coSoId);
            if (listCB.size() == 0) {
                dtCanBoResponseMessage.setStatus(false);
                dtCanBoResponseMessage.setMessage("Import không đúng định dạng của file excel mẫu");
            }
            Map<String, String> map = new HashMap<>();
            addDataInMap(map, coSoId);

            listCB.parallelStream().forEach(canbo -> {

                if (canbo.getEmailFE().isEmpty() || canbo.getEmailFPT().isEmpty() ||
                        canbo.getSoDienThoai().isEmpty() || canbo.getTenTaiKhoan().isEmpty() ||
                        canbo.getTenCanBo().isEmpty() || canbo.getVaiTro().isEmpty()) {
                    dtCanBoResponseMessage.setStatus(false);
                    dtCanBoResponseMessage.setMessage("Không được để trống");
                    return;
                }

                if (map.get(canbo.getEmailFE()) != null) {
                    dtCanBoResponseMessage.setStatus(false);
                    dtCanBoResponseMessage.setMessage("Email Fe bị trùng");
                    return;
                }

                if (map.get(canbo.getEmailFPT()) != null) {
                    dtCanBoResponseMessage.setStatus(false);
                    dtCanBoResponseMessage.setMessage("Email Fpt bị trùng");
                    return;
                }

                if (map.get(canbo.getTenTaiKhoan()) != null) {
                    dtCanBoResponseMessage.setStatus(false);
                    dtCanBoResponseMessage.setMessage("Tên tài khoản bị trùng");
                    return;
                }

                if (!canbo.getVaiTro().equals("DAO_TAO")
                        && !canbo.getVaiTro().equals("CHU_NHIEM_BO_MON")
                        && !canbo.getVaiTro().equals("QUAN_TRI_VIEN")
                ) {
                    dtCanBoResponseMessage.setStatus(false);
                    dtCanBoResponseMessage.setMessage("Vai trò không đúng định dạng");
                    return;
                }

                CanBo cb = new CanBo();
                cb.setEmailFe(canbo.getEmailFE());
                cb.setEmailFpt(canbo.getEmailFPT());
                cb.setTenCanBo(canbo.getTenCanBo());
                cb.setSoDienThoai(canbo.getSoDienThoai());
                if (canbo.getVaiTro().equals("DAO_TAO")) {
                    cb.setVaiTro(VaiTro.DAO_TAO);
                } else if (canbo.getVaiTro().equals("CHU_NHIEM_BO_MON")) {
                    cb.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
                } else if (canbo.getVaiTro().equals("QUAN_TRI_VIEN")) {
                    cb.setVaiTro(VaiTro.QUAN_TRI_VIEN);
                }
                cb.setCoSoId(coSoId);
                cb.setTenTaiKhoan(canbo.getTenTaiKhoan());
                listCanBo.put(cb.getTenTaiKhoan(), cb);
            });
            if (dtCanBoResponseMessage.getStatus() == true) {
                dtCanBoRepository.saveAll(listCanBo.values());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dtCanBoResponseMessage.setStatus(false);
            dtCanBoResponseMessage.setMessage("Lỗi hệ thống");
            return dtCanBoResponseMessage;
        }
        return dtCanBoResponseMessage;
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

    public void addDataInMap(Map<String, String> mapAll, String coSoId) {
        List<String> listEmailFE = dtCanBoRepository.findAllEmailFe(coSoId);
        setDataInMap(mapAll, listEmailFE);

        List<String> listEmailFpt = dtCanBoRepository.findAllByEmailFPT(coSoId);
        setDataInMap(mapAll, listEmailFpt);

        List<String> listTenTaiKhoan = dtCanBoRepository.finAllTenTaiKhoan(coSoId);
        setDataInMap(mapAll, listTenTaiKhoan);
    }

    public void setDataInMap(Map<String, String> mapSimple, List<String> listStr) {
        for (String s : listStr) {
            mapSimple.put(s, s);
        }
    }

}

